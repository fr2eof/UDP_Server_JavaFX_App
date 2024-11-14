package com.practice.command;

import com.practice.Main;
import com.practice.database.DataBaseManager;
import com.practice.dto.CommandResponseDto;
import com.practice.exception.ScriptReadingException;
import com.practice.exception.WrongAmountOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveByIdCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(RemoveByIdCommand.class);

    public RemoveByIdCommand() {
        super("remove_by_id", "remove an element from a collection by its id");
    }

    public boolean execute(Object[] args) {
        try {
            Main.queueToResponse.add(new CommandResponseDto(DataBaseManager.removeById((String) args[0], (Long) args[1])));
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
