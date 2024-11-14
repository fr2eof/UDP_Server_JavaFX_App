package com.practice.invoker;

import com.practice.command.AbstractCommand;
import com.practice.exception.WrongArgumentTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Invoker {
    private final Map<String, AbstractCommand> commands;
    private static final Logger log = LoggerFactory.getLogger(Invoker.class);

    public Invoker(Map<String, AbstractCommand> commands) {
        this.commands = commands;
    }

    public boolean executeCommand(Object[] args) {
        String line = (String) args[0];
        if (line != null) {
            line = line.trim();
            String[] parsedLine = line.split(" ");
            Object[] argsArray = new Object[parsedLine.length + 2];
            //add only 1, taking into account that the command name is not its argument
            argsArray[argsArray.length - 3] = args[1];
            argsArray[argsArray.length - 2] = args[2];
            argsArray[argsArray.length - 1] = args[3];
            if (parsedLine.length - 1 >= 0)
                System.arraycopy(parsedLine, parsedLine.length - 1, argsArray, 0, parsedLine.length - 1);
            if (commands.get(parsedLine[0]) != null) {
                AbstractCommand command = commands.get(parsedLine[0]);
                try {
                    return command.execute(argsArray);
                } catch (WrongArgumentTypeException ex) {
                    log.error("Invalid argument type entered {}", ex.getMessage());
                    return false;
                }
            }
            return false;
        }
        return false;
    }
}
