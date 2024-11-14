package com.practice.command;

import com.practice.dto.CommandRequestDto;
import com.practice.exception.WrongAmountOfArgumentsException;
import com.practice.network.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.transformer.Transformer;

public class RemoveByIdCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(RemoveByIdCommand.class);

    public RemoveByIdCommand() {
        super("remove_by_id");
    }

    public boolean execute(Object[] args) {
        try {
            if (args.length != 3) {
                throw new WrongAmountOfArgumentsException();
            }
            Object[] arguments = (Object[]) args[2];

            CommandRequestDto crd = (CommandRequestDto) args[1];
            RequestSender requestSender = (RequestSender) args[0];
            crd.setCommandName("remove_by_id");
            crd.setCommandArgs(new Object[]{null, arguments[0]});
            requestSender.sendRequest(Transformer.writeObject(crd));
            return true;
        } catch (WrongAmountOfArgumentsException var5) {
            log.error("One arguments in {}", this.getName());
            return false;
        } catch (NumberFormatException var6) {
            log.error("Not int in argument {}", this.getName());
            return false;
        } catch (Exception var7) {
            log.error("Sending a request{}", var7.getMessage());
            return false;
        }
    }
}
