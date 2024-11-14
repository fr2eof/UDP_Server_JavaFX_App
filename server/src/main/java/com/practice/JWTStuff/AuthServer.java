//package com.practice.JWTStuff;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.DatagramChannel;
//
//public class AuthServer {
//
//    private static final int PORT = 9000;
//
//    public static void main(String[] args) throws IOException {
//        DatagramChannel channel = DatagramChannel.open();
//        channel.bind(new InetSocketAddress(PORT));
//
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//
//        while (true) {
//            buffer.clear();
//            InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);
//            buffer.flip();
//
//            String received = new String(buffer.array(), 0, buffer.limit());
//            if (received.startsWith("AUTH")) {
//                String userId = authenticateUser(received); // Здесь должна быть ваша логика аутентификации
//                if (userId != null) {
//                    String accessToken = JwtUtil.generateAccessToken(userId);
//                    String refreshToken = JwtUtil.generateRefreshToken(userId);
//                    String response = "ACCESS_TOKEN:" + accessToken + "|REFRESH_TOKEN:" + refreshToken;
//                    buffer.clear();
//                    buffer.put(response.getBytes());
//                    buffer.flip();
//                    channel.send(buffer, clientAddress);
//                }
//            }
//        }
//    }
//
//    private static String authenticateUser(String authData) {
//        // Здесь должна быть ваша логика аутентификации
//        // Например, проверка логина и пароля
//        return "user123"; // Возвращаем userId в случае успешной аутентификации
//    }
//}