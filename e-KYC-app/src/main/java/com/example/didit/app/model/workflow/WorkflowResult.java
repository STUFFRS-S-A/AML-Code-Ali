package com.example.didit.app.model.workflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class WorkflowResult {
    private UUID uuid;
    @JsonProperty("workflow_id")
    private UUID workflowId;
    @JsonProperty("workflow_label")
    private String workflowLabel;
    @JsonProperty("workflow_type")
    private String workflowType;
    @JsonProperty("is_default")
    private boolean isDefault;
    @JsonProperty("is_archived")
    private boolean isArchived;
    @JsonProperty("total_price")
    private double totalPrice;
    @JsonProperty("min_price")
    private double minPrice;
    @JsonProperty("max_price")
    private double maxPrice;
    private String features;
    @JsonProperty("is_simple_workflow")
    private boolean isSimpleWorkflow;
    @JsonProperty("is_editable")
    private boolean isEditable;
    @JsonProperty("workflow_url")
    private String workflowUrl;
    @JsonProperty("max_retry_attempts")
    private int maxRetryAttempts;
    @JsonProperty("retry_window_days")
    private int retryWindowDays;
    @JsonProperty("session_expiration_time")
    private long sessionExpirationTime;
    private int version;
    private String status;
    @JsonProperty("has_draft")
    private boolean hasDraft;
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
