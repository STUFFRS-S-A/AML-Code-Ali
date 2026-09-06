package com.example.didit.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "user_sessions")
@Data
@Entity
public class UserSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workflowId;

    private Long userId;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private String sessionId;


    public static UserSessionEntity toModel(String workflowId, Long userId,String sessionId) {
        UserSessionEntity response = new UserSessionEntity();
        response.setWorkflowId(workflowId);
        response.setUserId(userId);
        response.setSessionId(sessionId);
        return response;
    }
}
