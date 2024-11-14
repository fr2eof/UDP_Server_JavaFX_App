package com.practice.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.Main;
import com.practice.dto.CommandResponseDto;
import com.practice.exception.ScriptReadingException;
import com.practice.exception.WrongAmountOfArgumentsException;

public class HelpCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(HelpCommand.class);

    public HelpCommand() {
        super("help", "Display help on available commands");
    }

    public boolean execute(Object[] args) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Main.commandMap.values().forEach(cmd -> {
                stringBuilder.append("• ").append(cmd.getName()).append(": ").append(cmd.getDescription()).append("\n");
            });
            Main.queueToResponse.add(new CommandResponseDto(stringBuilder.toString()));
            return true;
        } catch (WrongAmountOfArgumentsException var8) {
            log.error("No arguments in " + this.getName());
            return false;
        } catch (ScriptReadingException | NullPointerException var9) {
            log.error("Reading from script " + var9.getMessage());
            return false;
        } catch (Exception var10) {
            log.error("Sending a response " + var10.getMessage());
            return false;
        }
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }
}
