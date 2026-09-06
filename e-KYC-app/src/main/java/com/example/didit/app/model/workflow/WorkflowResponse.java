package com.example.didit.app.model.workflow;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowResponse {
    private int count;
    private String next;
    private String previous;
    private List<WorkflowResult> results;
}
