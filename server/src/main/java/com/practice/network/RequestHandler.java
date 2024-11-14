package com.practice.network;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestHandler {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final DatagramChannel channel;
    @Getter
    private InetSocketAddress clientAddress;

    public RequestHandler(DatagramChannel channel) {
        this.channel = channel;
    }

    public byte[] receiveRequest() {
        try {
            int capacity = 4096;
            ByteBuffer buffer = ByteBuffer.allocate(capacity);
            buffer.clear();
            this.clientAddress = (InetSocketAddress)this.channel.receive(buffer);
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            return data;
        } catch (IOException var4) {
            log.error("[REQUEST HANDLER]: Channel cannot receive " + var4.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            this.channel.close();
        } catch (IOException var2) {
            log.error("[REQUEST HANDLER]: The channel cannot be closed " + var2.getMessage());
        }

    }

}
