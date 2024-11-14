package com.practice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandRequestDto extends AbstractCommandDto {
    private String commandName;
    private Object[] commandArgs;
    private String login;
    private String password;
    private boolean check;

    public CommandRequestDto(String commandName, Object[] commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }

    public CommandRequestDto() {
    }

    public CommandRequestDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
