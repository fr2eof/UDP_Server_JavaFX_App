package com.practice.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestSender {
    private static final Logger log = LoggerFactory.getLogger(RequestSender.class);
    private final DatagramChannel channel;

    public RequestSender(DatagramChannel channel) {
        this.channel = channel;
    }

    public void sendRequest(byte[] message) {
        try {
            this.channel.write(ByteBuffer.wrap(message));
        } catch (IOException ex) {
            log.error("Writing buffer {}", ex.getMessage());
        }

    }

    public void close() {
        try {
            this.channel.close();
        } catch (IOException ex) {
            log.error("Channel closing {}", ex.getMessage());
        }

    }
}
