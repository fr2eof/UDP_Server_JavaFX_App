//package com.practice.JWTStuff;
//
//import io.jsonwebtoken.Jwts;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.DatagramChannel;
//
//import static com.practice.JWTStuff.JwtUtil.SECRET_KEY;
//
//public class RefreshTokenServer {
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
//            if (received.startsWith("REFRESH")) {
//                String refreshToken = received.split(" ")[1];
//                String userId = validateRefreshToken(refreshToken); // Здесь должна быть ваша логика проверки refresh токена
//                if (userId != null) {
//                    String newAccessToken = JwtUtil.generateAccessToken(userId);
//                    String newRefreshToken = JwtUtil.generateRefreshToken(userId);
//                    String response = "ACCESS_TOKEN:" + newAccessToken + "|REFRESH_TOKEN:" + newRefreshToken;
//                    buffer.clear();
//                    buffer.put(response.getBytes());
//                    buffer.flip();
//                    channel.send(buffer, clientAddress);
//                }
//            }
//        }
//    }
//
//    private static String validateRefreshToken(String refreshToken) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY)
//                    .build()
//                    .parseClaimsJws(refreshToken)
//                    .getBody()
//                    .getSubject();
//        } catch (Exception e) {
//            return null; // Возвращаем null в случае ошибки
//        }
//    }
//}