package com.practice.jwt;//package jwt;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.DatagramChannel;
//
//public class RefreshTokenClient {
//
//    public static void main(String[] args) throws IOException {
//        DatagramChannel channel = DatagramChannel.open();
//        channel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
//
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        String refreshRequest = "REFRESH " + refreshToken; // Предположим, что refreshToken уже сохранен
//        buffer.put(refreshRequest.getBytes());
//        buffer.flip();
//        channel.send(buffer, new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
//
//        buffer.clear();
//        channel.receive(buffer);
//        buffer.flip();
//
//        String response = new String(buffer.array(), 0, buffer.limit());
//        String[] tokens = response.split("\\|");
//        String newAccessToken = tokens[0].split(":")[1];
//        String newRefreshToken = tokens[1].split(":")[1];
//
//        System.out.println("New Access Token: " + newAccessToken);
//        System.out.println("New Refresh Token: " + newRefreshToken);
//
//        // Сохраните новые токены в безопасном месте
//    }
//}