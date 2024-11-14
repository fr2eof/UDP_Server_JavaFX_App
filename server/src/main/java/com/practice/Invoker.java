package com.practice;


import com.practice.command.AbstractCommand;
import com.practice.dto.CommandRequestDto;

import java.util.Map;

public class Invoker {
    private final Map<String, AbstractCommand> commands;

    public Invoker(Map<String, AbstractCommand> commands) {
        this.commands = commands;
    }

    public boolean executeCommand(CommandRequestDto dto) {
        AbstractCommand command = commands.get(dto.getCommandName());
        return command.execute(dto.getCommandArgs());
    }
}
