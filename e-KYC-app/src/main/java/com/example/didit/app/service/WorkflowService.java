package com.example.didit.app.service;

import com.example.didit.app.model.workflow.WorkflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkflowService {

    private final RestTemplate restTemplate;

    @Value("${didit.api.key}")
    private String apiKey;

    private static final String WORKFLOWS_URL = "https://verification.didit.me/v3/workflows/";

    public WorkflowResponse getWorkflows() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<WorkflowResponse> response = restTemplate.exchange(
                    WORKFLOWS_URL,
                    HttpMethod.GET,
                    entity,
                    WorkflowResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Error fetching workflows: {}", response.getStatusCode());
                throw new RuntimeException("Failed to fetch workflows from Didit API");
            }
        } catch (Exception e) {
            log.error("Exception occurred while calling Didit API", e);
            throw new RuntimeException("Error calling Didit API: " + e.getMessage());
        }
    }
}
