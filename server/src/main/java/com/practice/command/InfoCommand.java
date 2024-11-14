package com.practice.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.Main;
import com.practice.database.DataBaseManager;
import com.practice.dto.CommandResponseDto;
import com.practice.exception.ScriptReadingException;
import com.practice.exception.WrongAmountOfArgumentsException;

public class InfoCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(InfoCommand.class);

    public InfoCommand() {
        super("info", "Print information about the collection to standard output.");
    }

    public boolean execute(Object[] args) {
        try {
            CommandResponseDto commandResponseDto = new CommandResponseDto("There are "+ DataBaseManager.size((String) args[0]) + " elements in your database");
            Main.queueToResponse.add(commandResponseDto);
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
    @Override
    public String getDescription() {
        return super.getDescription();
    }
}
