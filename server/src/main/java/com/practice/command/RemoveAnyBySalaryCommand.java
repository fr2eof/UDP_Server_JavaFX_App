package com.practice.command;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.Main;
import com.practice.database.DataBaseManager;
import com.practice.dto.CommandResponseDto;
import com.practice.exception.ScriptReadingException;
import com.practice.exception.WrongAmountOfArgumentsException;

public class RemoveAnyBySalaryCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(RemoveAnyBySalaryCommand.class);

    public RemoveAnyBySalaryCommand() {
        super("remove_any_by_salary", "remove one element from the collection whose salary field value is equivalent to the given one");
    }

    public boolean execute(Object[] args) {
        try {
            Main.queueToResponse.add(new CommandResponseDto(DataBaseManager.removeAnyBySalary((String) args[0], (Integer) args[1])));
            return true;
        } catch (WrongAmountOfArgumentsException var8) {
            log.error("No arguments in " + this.getName());
            return false;
        } catch (ScriptReadingException | NullPointerException var9) {
            log.error("Reading from script "+var9.getMessage());
            return false;
        } catch (Exception var10) {
            log.error("Sending a response "+var10.getMessage());
            return false;
        }
    }
}
