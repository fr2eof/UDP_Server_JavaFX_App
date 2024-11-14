package com.practice.command.authentication;


import com.practice.command.AbstractCommand;
import com.practice.dto.CommandRequestDto;
import com.practice.exception.WrongAmountOfArgumentsException;
import com.practice.network.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.transformer.Transformer;

public class LoginCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);

    public LoginCommand() {
        super("login");
    }

    public boolean execute(Object[] args) {
        try {
            if (args.length != 3) {
                throw new WrongAmountOfArgumentsException();
            } else {
                Object[] arguments = (Object[]) args[2];
                String login = (String) arguments[0];
                String password = (String) arguments[1];

//                backend.BackendClientApp.user = login;
                CommandRequestDto crd = (CommandRequestDto) args[1];
                RequestSender requestSender = (RequestSender) args[0];
                crd.setCommandName("login");
                crd.setCommandArgs(new Object[]{login, password});
                requestSender.sendRequest(Transformer.writeObject(crd));
                return true;
            }
        } catch (WrongAmountOfArgumentsException ex) {
            log.error("No arguments in {}", ex.getMessage());
            return false;
        }
    }
}