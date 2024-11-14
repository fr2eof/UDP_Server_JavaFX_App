package com.practice.command;

import com.practice.dto.CommandRequestDto;
import com.practice.exception.WrongAmountOfArgumentsException;
import com.practice.network.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.transformer.Transformer;

public class ClearCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(ClearCommand.class);

    public ClearCommand() {
        super("clear");
    }

    public boolean execute(Object[] args) {
        try {
            if (args.length != 3) {
                throw new WrongAmountOfArgumentsException();
            }
            CommandRequestDto crd = (CommandRequestDto) args[1];
            RequestSender requestSender = (RequestSender) args[0];
            crd.setCommandName("clear");
            crd.setCommandArgs(new Object[]{null});
            requestSender.sendRequest(Transformer.writeObject(crd));
            return true;
        } catch (WrongAmountOfArgumentsException var5) {
            log.error("No arguments in " + this.getName());
            return false;
        } catch (Exception var6) {
            log.error("Sending a request");
            return false;
        }
    }
}
