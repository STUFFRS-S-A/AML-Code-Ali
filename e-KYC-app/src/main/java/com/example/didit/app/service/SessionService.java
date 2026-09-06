package com.example.didit.app.service;

import com.example.didit.app.entity.UserSessionEntity;
import com.example.didit.app.exception.CustomException;
import com.example.didit.app.model.session.CreateSessionRequest;
import com.example.didit.app.model.session.CreateSessionResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.didit.app.repository.UserSessionRepository;
import com.example.didit.app.security.AuthManager;
import com.example.didit.app.util.TimeBasedUUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class SessionService {

    private final RestTemplate restTemplate;
    private final AuthManager authManager;
    private final UserSessionRepository userSessionRepository;
    private final AmlService amlService;
    private String vendor = "vendor-";

    @Value("${didit.api.key}")
    private String apiKey;

    private static final String SESSIONS_URL = "https://verification.didit.me/v3/session/";
    private static final String SESSIONS_LIST_URL = "https://verification.didit.me/v3/sessions";

    public CreateSessionResponse createUserSession(CreateSessionRequest request) {

        Long userId = authManager.getUserId();
        String vendorData = vendor+userId+ TimeBasedUUID.generateTimeBasedUUID();
        request.setVendorData(vendorData);
        CreateSessionResponse createSessionResponse = createSession(request);
        if(createSessionResponse.getWorkflowId() != null){
            UserSessionEntity userSessionEntity = UserSessionEntity.toModel(createSessionResponse.getWorkflowId()
            ,userId,createSessionResponse.getSessionId());
            userSessionRepository.save(userSessionEntity);
            return createSessionResponse;
        }
        else {
            return createSessionResponse;
        }


    }

    public CreateSessionResponse createSession(CreateSessionRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/json");
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String jsonBody = mapper.writeValueAsString(request);
            log.info("Sending JSON to Didit: {}", jsonBody);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<CreateSessionResponse> response = restTemplate.exchange(
                    SESSIONS_URL,
                    HttpMethod.POST,
                    entity,
                    CreateSessionResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Error creating session: {}", response.getStatusCode());
                throw new RuntimeException("Failed to create session from Didit API");
            }
        } catch (Exception e) {
            log.error("Exception occurred while calling Didit API", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public Object getSessions(int offset, int limit) {
        try {
            Long userId = authManager.getUserId();
            String vendorData = vendor+userId;
            String url = SESSIONS_LIST_URL + "?offset=" + offset + "&limit=" + limit + "&search=" + vendorData;

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Error getting sessions: {}", response.getStatusCode());
                throw new RuntimeException("Failed to get sessions from Didit API");
            }
        } catch (Exception e) {
            log.error("Exception occurred while calling Didit API", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public Object getSessionDecision(String sessionId) {
        try {
            String url = SESSIONS_URL + sessionId + "/decision/";

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> country = extractCountry(response.getBody());
                Map<String, Object> body = (Map<String, Object>) response.getBody();

                if (country != null) {
                    String longName  = (String) country.get("long_name");   // "Pakistan"
                    String shortName = (String) country.get("short_name");  // "PK"
                    String risk = amlService.calculareNationalityRisk(shortName);

                    body.put("amlRisk", risk);
                }
                else{
                    body.put("amlRisk","UNKNOWN");
                }

                return body;
            } else {
                log.error("Error getting session decision: {}", response.getStatusCode());
                throw new RuntimeException("Failed to get session decision from Didit API");
            }
        } catch (Exception e) {
            log.error("Exception occurred while calling Didit API for decision", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public Object updateSessionStatus(String sessionId, String newStatus) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/json");

            Map<String, String> body = new HashMap<>();
            body.put("new_status", newStatus);

            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(body);
            log.info("Sending status update to Didit for session {}: {}", sessionId, jsonBody);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            String url = SESSIONS_URL + sessionId + "/update-status";

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    entity,
                    Object.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Error updating session status: {}", response.getStatusCode());
                throw new RuntimeException("Failed to update session status from Didit API");
            }
        }
        catch (HttpClientErrorException e) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(e.getResponseBodyAsString(), Object.class);
            } catch (Exception ex) {
                throw new RuntimeException(e.getResponseBodyAsString());
            }
        }
        catch (Exception e) {
            log.error("Exception occurred while calling Didit API", e);
            throw new CustomException(e.getMessage());
        }
    }

    public Map<String, Object> extractCountry(Object sessionDecision) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Convert Object to Map
            Map<String, Object> root = mapper.convertValue(sessionDecision, new TypeReference<Map<String, Object>>() {});

            // Navigate: id_verifications[0] -> parsed_address -> raw_results -> address_components
            List<Map<String, Object>> idVerifications = (List<Map<String, Object>>) root.get("id_verifications");
            if (idVerifications == null || idVerifications.isEmpty()) return null;

            Map<String, Object> parsedAddress = (Map<String, Object>) idVerifications.get(0).get("parsed_address");
            if (parsedAddress == null) return null;

            Map<String, Object> rawResults = (Map<String, Object>) parsedAddress.get("raw_results");
            if (rawResults == null) return null;

            List<Map<String, Object>> addressComponents = (List<Map<String, Object>>) rawResults.get("address_components");
            if (addressComponents == null) return null;

            // Find the component whose "types" list contains "country"
            return addressComponents.stream()
                    .filter(component -> {
                        List<String> types = (List<String>) component.get("types");
                        return types != null && types.contains("country");
                    })
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            log.error("Failed to extract country from session decision", e);
            return null;
        }
    }
}
