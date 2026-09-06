package com.appopay.aml.rest;

import com.appopay.aml.Exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;


@Service
public class RestClient {

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public String checkStatus(String mobileNo, String deviceId) {
        CheckDeviceStatusResponse checkDeviceStatusResponse = null;
        try {

            MediaType JSON = MediaType.get("application/json; charset=utf-8");

            // Create the JSON body
            String jsonBody = String.format("{\"mobileNo\":\"%s\", \"deviceId\":\"%s\"}", mobileNo, deviceId);
            RequestBody requestBody = RequestBody.create(jsonBody, JSON);

            // Build the request
            Request request = new Request.Builder()
                    .url("http://3.13.197.45:8081/device/checkStatus")
                    .post(requestBody)
                    .addHeader("accept", "*/*")
                    .build();

            // Execute the request
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new CustomException("Unexpected HTTP status code: " + + response.code() + response.body());
                }
                checkDeviceStatusResponse = objectMapper.readValue(response.body().string(), CheckDeviceStatusResponse.class);
                System.out.println("checkDeviceStatusResponse.getStatus()");
                System.out.println(checkDeviceStatusResponse.getMessage());
                System.out.println("checkDeviceStatusResponse received successfully.");
            }

        } catch (Exception e) {
//            throw new CustomException("Exception" + e);
        }
        return checkDeviceStatusResponse.getMessage();
    }
}
