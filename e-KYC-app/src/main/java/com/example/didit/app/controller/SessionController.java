package com.example.didit.app.controller;

import com.example.didit.app.model.session.CreateSessionRequest;
import com.example.didit.app.model.session.CreateSessionResponse;
import com.example.didit.app.model.workflow.WorkflowResponse;
import com.example.didit.app.service.SessionService;
import com.example.didit.app.service.WorkflowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;


    @PostMapping("/create")
    public CreateSessionResponse createSession(@Valid @RequestBody CreateSessionRequest request) {
        System.out.println("Received request: " + request);
        return sessionService.createUserSession(request);
    }

    @GetMapping("/list")
    public Object getSessions(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {
        return sessionService.getSessions(offset, limit);
    }

    @GetMapping("/{sessionId}/decision")
    public Object getSessionDecision(@PathVariable String sessionId) {
        return sessionService.getSessionDecision(sessionId);
    }

    @PostMapping("/update-status/{sessionId}/{status}")
    public Object updateStatus(@PathVariable String sessionId,@PathVariable String status) {
        return sessionService.updateSessionStatus(sessionId,status);
    }

}
