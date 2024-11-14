package com.practice.command.auth;

import com.practice.command.AbstractCommand;
import com.practice.database.DataBaseManager;
import com.practice.dto.CommandResponseDto;
import com.practice.exception.UserLoginExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.practice.Main.queueToResponse;

public class RegistrationCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(RegistrationCommand.class);

    public RegistrationCommand() {
        super("registration", "registration in the system");
    }

    public boolean execute(Object[] args) {
        try {
            if(!DataBaseManager.register((String) args[0], (String) args[1])){
                queueToResponse.add(new CommandResponseDto("The password does not match this user"));
            }else{
                queueToResponse.add(new CommandResponseDto("Registration completed successfully\n" +
                        "For security reasons, you will need to re-login every 10 minutes."));
            }

        } catch (UserLoginExistsException e) {
            queueToResponse.add(new CommandResponseDto("A user with this login already exists. Try something new"));
        }
        return true;
    }
}
