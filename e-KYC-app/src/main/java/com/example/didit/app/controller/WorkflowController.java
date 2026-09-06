package com.example.didit.app.controller;

import com.example.didit.app.model.workflow.WorkflowResponse;
import com.example.didit.app.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    @GetMapping("/get-workflows")
    public WorkflowResponse getWorkflows() {
        return workflowService.getWorkflows();
    }

}
