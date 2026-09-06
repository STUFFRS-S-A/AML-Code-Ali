package com.appopay.visa.service;

import com.appopay.visa.entity.KYCInfo;
import com.appopay.visa.model.kyc.AMLRequestDTO;
import com.appopay.visa.model.kyc.FaceMatchRequestDTO;
import com.appopay.visa.model.kyc.IdVerificationRequestDTO;
import com.appopay.visa.repository.KYCRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.IOException;
import java.util.Optional;

@Service
public class KYCService {

    @Autowired
    private KYCRepository kycRepository;

    @Value("${didit.api.key}")
    private String apiKey;

    public void updateStatus(String sessionId, String status) throws Exception {
        Optional<KYCInfo> optionalKYCInfo = kycRepository.findBySessionId(sessionId);
        if (optionalKYCInfo.isEmpty()) {
            throw new Exception("no record found against session id: " + sessionId);
        }
        if (status == null || status.isEmpty()) {
            throw new Exception("status is empty against session id: " + sessionId);
        }

        KYCInfo kycInfo = optionalKYCInfo.get();
        kycInfo.setStatus(status);
        kycRepository.save(kycInfo);
    }

    public String startVerification() throws IOException {
        String verificationResponse = createVerificationSession();
        String sessionId = getSessionId(verificationResponse);
        String status = getStatus(verificationResponse);

        KYCInfo kycInfo = new KYCInfo();
        kycInfo.setSessionId(sessionId);
        kycInfo.setStatus(status);

        kycRepository.save(kycInfo);

        return verificationResponse;
    }

    public String createVerificationSession() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String jsonBody = "{\n" +
                "  \"workflow_id\": \"0af30d1a-cee5-4cd9-8f2a-a7d640686bc4\",\n" +
                "  \"callback\": \"https://appopay-frontend.s3.us-east-2.amazonaws.com/thankyou.html\",\n" +
                "  \"callback_method\": \"both\"\n" +
                "}";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder().url("https://verification.didit.me/v2/session/").addHeader("accept", "application/json").addHeader("content-type", "application/json").addHeader("x-api-key", apiKey).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Request failed: " + response.code() + " " + response.message());
                return null;
            }

            return response.body() != null ? response.body().string() : null;
        }
    }

    public JsonNode getDecision(String sessionId) throws Exception {
        String urlString = "https://verification.didit.me/v2/session/" + sessionId + "/decision/";
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("x-api-key", apiKey);

        int status = conn.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(status > 299 ? conn.getErrorStream() : conn.getInputStream()));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            content.append(line);
        }

        in.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(content.toString());
    }

    public String getSessionId(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonString);
            return root.path("session_id").asText(null); // returns null if missing
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStatus(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonString);
            return root.path("status").asText(null); // returns null if missing
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode performIdVerification(IdVerificationRequestDTO dto) throws IOException {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        if (dto.getFront() != null && !dto.getFront().isEmpty()) {
            RequestBody fileBody = RequestBody.create(dto.getFront().getBytes(), MediaType.parse(dto.getFront().getContentType()));

            multipartBuilder.addFormDataPart("front_image", dto.getFront().getOriginalFilename(), fileBody);
        }

        if (dto.getBack() != null && !dto.getBack().isEmpty()) {
            RequestBody fileBody = RequestBody.create(dto.getBack().getBytes(), MediaType.parse(dto.getBack().getContentType()));

            multipartBuilder.addFormDataPart("back_image", dto.getBack().getOriginalFilename(), fileBody);
        }

        if (dto.getPerfrom_liveliness() != null) {
            multipartBuilder.addFormDataPart("perform_document_liveness", dto.getPerfrom_liveliness().toString());
        }

        if (dto.getMinimum_age() != null) {
            multipartBuilder.addFormDataPart("minimum_age", dto.getMinimum_age().toString());
        }

        if (dto.getExpiration_date_not_detected_action() != null) {
            multipartBuilder.addFormDataPart("expiration_date_not_detected_action", dto.getExpiration_date_not_detected_action().name());
        }

        if (dto.getInvalid_mrz_action() != null) {
            multipartBuilder.addFormDataPart("invalid_mrz_action", dto.getInvalid_mrz_action().name());
        }

        if (dto.getInconsistent_data_action() != null) {
            multipartBuilder.addFormDataPart("inconsistent_data_action", dto.getInconsistent_data_action().name());
        }

        RequestBody requestBody = multipartBuilder.build();

        Request request = new Request.Builder().url("https://verification.didit.me/v2/id-verification/").post(requestBody).addHeader("accept", "application/json").addHeader("x-api-key", apiKey).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }

            String responseBody = response.body().string();
            return objectMapper.readTree(responseBody);
        }
    }

    public JsonNode faceMatch(FaceMatchRequestDTO dto) throws IOException {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        if (dto.getUser_image() != null && !dto.getUser_image().isEmpty()) {
            RequestBody fileBody = RequestBody.create(dto.getUser_image().getBytes(), MediaType.parse(dto.getUser_image().getContentType()));

            multipartBuilder.addFormDataPart("user_image", dto.getUser_image().getOriginalFilename(), fileBody);
        }

        if (dto.getRef_image() != null && !dto.getRef_image().isEmpty()) {
            RequestBody fileBody = RequestBody.create(dto.getRef_image().getBytes(), MediaType.parse(dto.getRef_image().getContentType()));

            multipartBuilder.addFormDataPart("ref_image", dto.getRef_image().getOriginalFilename(), fileBody);
        }

        if (dto.getFace_match_score_decline_threshold() != null) {
            multipartBuilder.addFormDataPart("face_match_score_decline_threshold", dto.getFace_match_score_decline_threshold().toString());
        }

        if (dto.getRotate_image() != null) {
            multipartBuilder.addFormDataPart("rotate_image", dto.getRotate_image().toString());
        }

        RequestBody requestBody = multipartBuilder.build();

        Request request = new Request.Builder().url("https://verification.didit.me/v2/face-match/").post(requestBody).addHeader("accept", "application/json").addHeader("x-api-key", apiKey).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }

            String responseBody = response.body().string();
            return objectMapper.readTree(responseBody);
        }

    }

    public JsonNode amlScreening(AMLRequestDTO dto) throws IOException {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        if (dto.getFull_name() != null) {
            multipartBuilder.addFormDataPart("full_name", dto.getFull_name());
        }

        if (dto.getDate_of_birth() != null) {
            multipartBuilder.addFormDataPart("date_of_birth", dto.getDate_of_birth());
        }

        if (dto.getNationality() != null) {
            multipartBuilder.addFormDataPart("nationality", dto.getNationality());
        }

        if (dto.getDocument_number() != null) {
            multipartBuilder.addFormDataPart("document_number", dto.getDocument_number());
        }

        if (dto.getAml_score_approve_threshold() != null) {
            multipartBuilder.addFormDataPart("aml_score_approve_threshold", dto.getAml_score_approve_threshold().toString());
        }

        if (dto.getInclude_adverse_media() != null) {
            multipartBuilder.addFormDataPart("include_adverse_media", dto.getInclude_adverse_media().toString());
        }

        RequestBody requestBody = multipartBuilder.build();

        Request request = new Request.Builder().url("https://verification.didit.me/v2/aml/").post(requestBody).addHeader("accept", "application/json").addHeader("x-api-key", apiKey).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }

            String responseBody = response.body().string();
            return objectMapper.readTree(responseBody);
        }
    }

}
