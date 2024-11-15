package com.practice.backend;

import com.practice.command.*;
import com.practice.command.authentication.LoginCommand;
import com.practice.command.authentication.RegistrationCommand;
import com.practice.command.stuff.Action;
import com.practice.dto.CommandRequestDto;
import com.practice.exception.ServerReachException;
import com.practice.invoker.Invoker;
import com.practice.network.RequestSender;
import com.practice.network.ResponseHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

public class BackendClientApp {
    //todo Такой параметр как продолжительность жизни токена рекомендую вынести в application.properties.
    private static final Logger log = LoggerFactory.getLogger(BackendClientApp.class);
    private static Invoker invoker;
    private static final int PORT = 8383;
    private static final String HOST = "localhost";
    private static RequestSender requestSender;
    private static ResponseHandler responseHandler;

    public BackendClientApp() {
    }

    public void startBackend() {
        invoker = new Invoker(fillCommandMap());
        channelOpening();
    }

    private static @NotNull Map<String, AbstractCommand> fillCommandMap() {
        Map<String, AbstractCommand> cmdMap = new HashMap<>();
        cmdMap.put("registration", new RegistrationCommand());
        cmdMap.put("login", new LoginCommand());

        cmdMap.put("add", new AddCommand());
//        cmdMap.put("add_if_max", new AddIfMaxCommand());
        cmdMap.put("clear", new ClearCommand());
        cmdMap.put("help", new HelpCommand());
        cmdMap.put("info", new InfoCommand());
        cmdMap.put("min_by_id", new MinByIdCommand());
        cmdMap.put("print_ascending", new PrintAscendingCommand());
        cmdMap.put("remove_any_by_salary", new RemoveAnyBySalaryCommand());
        cmdMap.put("remove_by_id", new RemoveByIdCommand());
        cmdMap.put("remove_first", new RemoveFirstCommand());
        cmdMap.put("show", new ShowCommand());
        cmdMap.put("update", new UpdateIdCommand());
        return cmdMap;
    }

    private static void channelOpening() {
        try {
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress serverAddress = new InetSocketAddress(HOST, PORT);
            channel.configureBlocking(true);
            channel.connect(serverAddress);
            requestSender = new RequestSender(channel);
            responseHandler = new ResponseHandler(channel);
        } catch (IOException ex) {
            log.error("Channel opening {}", ex.getMessage());
        }
    }

    public static Object[] go(Object[] arguments) {
        try {
            String line = (String) arguments[0];
            Object[] args = new Object[arguments.length - 1];
            System.arraycopy(arguments, 1, args, 0, arguments.length - 1);
            return new Object[]{invoker.executeCommand(new Object[]{line, requestSender, new CommandRequestDto(), args}), responseHandler.receiveResponse()};
        } catch (IOException | ServerReachException e) {
            return new Object[]{Action.ERROR, "Request was not sent"};
        }
    }
}
