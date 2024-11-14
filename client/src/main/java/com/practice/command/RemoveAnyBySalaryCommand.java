package com.practice.command;

import com.practice.dto.CommandRequestDto;
import com.practice.exception.WrongAmountOfArgumentsException;
import com.practice.exception.WrongArgumentTypeException;
import com.practice.network.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.transformer.Transformer;

public class RemoveAnyBySalaryCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(RemoveAnyBySalaryCommand.class);

    public RemoveAnyBySalaryCommand() {
        super("remove_any_by_salary");
    }

    public boolean execute(Object[] args) {
        try {
            if (args.length != 3) {
                throw new WrongAmountOfArgumentsException();
            }
            try {
                int salary = Integer.parseInt((String) args[0]);
            } catch (NumberFormatException e) {
                throw new WrongArgumentTypeException();
            }
            CommandRequestDto crd = (CommandRequestDto) args[2];
            RequestSender requestSender = (RequestSender) args[1];
            crd.setCommandName("remove_any_by_salary");
            crd.setCommandArgs(new Object[]{null, Integer.parseInt((String) args[0])});
            requestSender.sendRequest(Transformer.writeObject(crd));
            return true;
        } catch (WrongAmountOfArgumentsException var5) {
            log.error("One arguments in " + this.getName());
            return false;
        } catch (WrongArgumentTypeException e) {
            throw e;
        } catch (Exception var6) {
            log.error("Sending a request" + var6.getMessage());
            return false;
        }
    }
}
