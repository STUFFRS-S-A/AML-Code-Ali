package com.example.didit.app;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KycTokenGenerator {

    private static final String MASTER_KEY = "A7B9C2D4E6F8G1H3J5K7L9M2N4P6Q8S1";

    public static void main(String[] args) throws Exception {
        // 1. Generate request_id - must be unique per request.
        //    Using epoch millis keeps it numeric and unique without needing a DB counter.
        String requestId = String.valueOf(System.currentTimeMillis());

        // 2. Generate request_date (DDMMYYYY) and request_time (HHMMSS) - "now"
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        // 3. Build the auth_token for these exact three values
        String authToken = buildAuthToken(requestId, requestDate, requestTime);

        // 4. Print everything you need to paste into Postman / curl
        System.out.println("request_id   = " + requestId);
        System.out.println("request_date = " + requestDate);
        System.out.println("request_time = " + requestTime);
        System.out.println("auth_token   = " + authToken);
    }

    static String buildAuthToken(String requestId, String requestDate, String requestTime) throws Exception {
        String tokenData = requestId + "|" + requestDate + "|" + requestTime;

        StringBuilder eData = new StringBuilder();
        StringBuilder errDesc = new StringBuilder();

        int status = AESEncryptText(MASTER_KEY, tokenData, eData, errDesc);
        if (status != 1) {
            throw new Exception("Failed to generate auth_token: " + errDesc);
        }
        return eData.toString();
    }

    static int AESEncryptText(String key, String data, StringBuilder eData, StringBuilder errDesc) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
                errDesc.append("Key length should be 16/24/32");
                return -1;
            }

            byte[] plainText = data.getBytes(StandardCharsets.UTF_8);

            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(plainText);

            byte[] cipherText = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, cipherText, 0, iv.length);
            System.arraycopy(encrypted, 0, cipherText, iv.length, encrypted.length);

            eData.append(Base64.getEncoder().withoutPadding().encodeToString(cipherText));
            return 1;
        } catch (Exception e) {
            errDesc.append(e.getMessage());
            return -1;
        }
    }
}