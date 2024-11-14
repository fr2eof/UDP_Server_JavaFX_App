package com.practice.command.auth;

import com.practice.command.AbstractCommand;
import com.practice.database.DataBaseManager;
import com.practice.dto.CommandResponseDto;
import com.practice.exception.LoginMatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.practice.Main.queueToResponse;

public class LoginCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);

    public LoginCommand() {
        super("login","logging into the system");
    }

    public boolean execute(Object[] args) {
        try {
            if(!DataBaseManager.login((String) args[0], (String) args[1])){
                queueToResponse.add(new CommandResponseDto("The password does not match this user"));
            }else{
                queueToResponse.add(new CommandResponseDto("You have successfully logged in. Welcome.\n" +
                        "For security reasons, you will need to re-login every 10 minutes."));
            }
        }catch (LoginMatchException e){
            queueToResponse.add(new CommandResponseDto("A user with this login was not found. Register"));
        }
        return true;
    }
}