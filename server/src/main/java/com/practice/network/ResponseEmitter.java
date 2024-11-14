package com.practice.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseEmitter {
    private final DatagramChannel channel;

    public ResponseEmitter(DatagramChannel channel) {
        this.channel = channel;
    }

    public void sendResponse(InetSocketAddress recipientAddress, byte[] response) throws IOException {
        int capacity = 4096;
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        buffer.put(response);
        buffer.flip();
        this.channel.send(buffer, recipientAddress);
    }

    public DatagramChannel getChannel() {
        return this.channel;
    }
}
