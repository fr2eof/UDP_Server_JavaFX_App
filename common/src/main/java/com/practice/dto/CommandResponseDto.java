package com.practice.dto;

import lombok.Getter;
import lombok.Setter;

import java.net.SocketAddress;

@Getter
@Setter
public class CommandResponseDto extends AbstractCommandDto {
    private String response;
    private SocketAddress socketAddress;

    public CommandResponseDto(String response) {
        this.response = response;
    }
}
