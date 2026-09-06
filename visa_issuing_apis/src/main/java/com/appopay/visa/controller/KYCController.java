package com.appopay.visa.controller;


import com.appopay.visa.model.ResponseDTO;
import com.appopay.visa.model.kyc.AMLRequestDTO;
import com.appopay.visa.model.kyc.FaceMatchRequestDTO;
import com.appopay.visa.model.kyc.IdVerificationRequestDTO;
import com.appopay.visa.service.KYCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/kyc")
public class KYCController {

    private static final String WEBHOOK_SECRET = "abMdbwGjA6-oZKmuZwrDPSsca4SfwjcMqGhe1HDoeF0";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private KYCService kycService;

    private static String bytesToHex(byte[] bytes) {
        var sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    @PostMapping("/start-session")
    public ResponseEntity<ResponseDTO> login() throws IOException {
        String response = kycService.startVerification();
        return ResponseEntity.ok().body(new ResponseDTO(response));
    }

    @GetMapping("/get-decision/{sessionId}")
    public ResponseEntity<JsonNode> getDecision(@PathVariable String sessionId) throws Exception {
        JsonNode response = kycService.getDecision(sessionId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(path = "/callback", consumes = "application/json")
    public ResponseEntity<String> handleDiditCallback(@RequestHeader("X-Signature") String signature, @RequestBody byte[] rawBody) {
        try {
            // 1. Compute HMAC-SHA256 of rawBody using your webhook secret
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(WEBHOOK_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            String expectedSig = mac.doFinal(rawBody).length > 0 ? bytesToHex(mac.doFinal(rawBody)) : "";
            mac.reset();

            if (!MessageDigest.isEqual(expectedSig.getBytes(StandardCharsets.UTF_8), signature.getBytes(StandardCharsets.UTF_8))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
            }

            // 2. Log the raw JSON payload
            String jsonText = new String(rawBody, StandardCharsets.UTF_8);
            System.out.println("Received Didit webhook payload:");
            System.out.println(jsonText);

            // 3. Optionally parse and handle specific events
            JsonNode json = mapper.readTree(rawBody);
            String status = json.path("status").asText();
            String sessionId = json.path("session_id").asText();
            if (json.has("decision")) {
                String decision = json.path("decision").asText();
                System.out.printf("➡ Session %s status=%s, decision=%s%n", sessionId, status, decision);
                kycService.updateStatus(sessionId, status);
            }

            return ResponseEntity.ok("Webhook received");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @PostMapping(value = "/idVerification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonNode> idVerification(@ModelAttribute IdVerificationRequestDTO request) throws IOException {
        JsonNode response = kycService.performIdVerification(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/faceMatch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonNode> faceMatch(@ModelAttribute FaceMatchRequestDTO request) throws IOException {
        JsonNode response = kycService.faceMatch(request);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping(value = "/amlScreening", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonNode> amlScreening(@ModelAttribute AMLRequestDTO request) throws IOException {
        JsonNode response = kycService.amlScreening(request);
        return ResponseEntity.ok().body(response);
    }



}
