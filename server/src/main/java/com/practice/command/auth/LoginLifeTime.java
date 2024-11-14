package com.practice.command.auth;



import com.practice.dto.CommandRequestDto;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Class simulating token lifetime
 *
 * @author fr2eof
 */
public class LoginLifeTime {
    private static final Map<InetSocketAddress, Object[]> tokens = new HashMap<>();
    private static final long lifeTime = 10L;
    public static void remove(InetSocketAddress inetSocketAddress){
        tokens.remove(inetSocketAddress);

    }
    public static boolean start(InetSocketAddress inetSocketAddress, CommandRequestDto dto) {
        switch (dto.getCommandName()) {
            case ("login"):
            case ("registration"):
                tokens.put(inetSocketAddress, new Object[]{dto.getCommandArgs()[0], LocalDateTime.now()});
                return true;
            default:
                if (tokens.containsKey(inetSocketAddress)) {
                    long min = Duration.between((LocalDateTime) tokens.get(inetSocketAddress)[1], LocalDateTime.now()).toMinutes();
                    if (min < lifeTime) {
                        remove(inetSocketAddress);
                        return true;
                    } else {
                    }
                }
                return false;
        }
    }
}
