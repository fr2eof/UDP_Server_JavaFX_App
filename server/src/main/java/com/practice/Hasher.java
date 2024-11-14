package com.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hasher implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Hasher.class);

    public Hasher() {
    }

    public static byte[] generateSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException var2) {
            log.error("Generating salt failed " + var2.getMessage());
            return null;
        }
    }

    public static String getHash(String line) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (line == null) {
                line = "";
            }
            byte[] bytes = md.digest(line.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Hashing password failed " + e.getMessage());
            return null;
        }
    }
}
