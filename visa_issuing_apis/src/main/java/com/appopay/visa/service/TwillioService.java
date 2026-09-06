package com.appopay.visa.service;

import com.appopay.visa.Exception.CustomException;
import com.appopay.visa.model.TwillioRequestDTO;
import com.appopay.visa.model.TwillioResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;


@Service
public class TwillioService {

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.number}")
    private String fromNumber;

    public TwillioResponseDTO sendSMS(String toNumber, String otp) {

        System.out.println("to num");
        System.out.println(toNumber);
        System.out.println("otp");
        System.out.println(otp);
        TwillioResponseDTO twillioResponse = null;
        try {


            String authString = accountSid + ":" + authToken;
            String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
            String basicAuth = "Basic " + encodedAuth;


//
//            // Message details
//            String body = "Hello, this is a test message!";
//            String from = "14243534350";
//            String to = "923222851644";

            // Form the request body
            RequestBody requestBody = new FormBody.Builder()
                    .add("Body", "[#]Your OTP IS:" + otp)
                    .add("From", fromNumber)
                    .add("To", toNumber)
                    .build();

            // Build the request
            Request request = new Request.Builder()
                    .url("https://api.twilio.com/2010-04-01/Accounts/" + accountSid + "/Messages.json")
                    .post(requestBody)
                    .addHeader("Authorization", basicAuth)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            System.out.println("basicAuth");
            System.out.println(encodedAuth);
            // Execute the request
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new CustomException("Unexpected HTTP status code: " + + response.code() + response.body());
                }
                twillioResponse = objectMapper.readValue(response.body().string(), TwillioResponseDTO.class);
                System.out.println("resp.getAccount_sid()");
                System.out.println(twillioResponse.getAccount_sid());
                System.out.println("Message sent successfully.");

            }

        }
        catch (Exception e) {
            throw new CustomException("Exception" + e);
        }
        return twillioResponse;
    }
}
