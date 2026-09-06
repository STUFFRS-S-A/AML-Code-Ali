package com.example.didit.app.model.session;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionResponse {

    @NotNull(message = "workflowId is mandatory")
    @JsonProperty("workflow_id")
    private String workflowId;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("session_number")
    private Integer sessionNumber;

    @JsonProperty("session_token")
    private String sessionToken;

    @JsonProperty("url")
    private String url;

    @JsonProperty("vendor_data")
    private String vendorData;

    @JsonProperty("metadata")
    private String metadata;

    @JsonProperty("status")
    private String status;

    @JsonProperty("callback")
    private String callback;

    @JsonProperty("workflow_version")
    private Integer workflowVersion;
}