package com.loginapp;

import java.util.Random;

public class Authenticator {
    private static String generatedCode;

    public static void send2FACode(String user) {
        generatedCode = String.format("%06d", new Random().nextInt(999999));
        System.out.println("2FA Code (simulate sending to email): " + generatedCode);
    }

    public static boolean verifyCode(String code) {
        return generatedCode != null && generatedCode.equals(code);
    }
}
