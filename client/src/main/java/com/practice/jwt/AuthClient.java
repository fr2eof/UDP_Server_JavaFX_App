package com.practice.jwt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class AuthClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9000;

    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String authRequest = "AUTH user:password";
        buffer.put(authRequest.getBytes());
        buffer.flip();
        channel.send(buffer, new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));

        buffer.clear();
        channel.receive(buffer);
        buffer.flip();

        String response = new String(buffer.array(), 0, buffer.limit());
        String[] tokens = response.split("\\|");
        String accessToken = tokens[0].split(":")[1];
        String refreshToken = tokens[1].split(":")[1];

        System.out.println("Access Token: " + accessToken);
        System.out.println("Refresh Token: " + refreshToken);

        // Сохраните токены в безопасном месте, например, в локальном хранилище
    }
}