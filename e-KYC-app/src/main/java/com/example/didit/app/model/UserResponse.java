package com.example.didit.app.model;

import com.example.didit.app.entity.UserMasterEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private Long institutionId;
    private String userName;
    private String email;
    private Boolean adUser;
    private boolean isActive;
    private String role;
    private Instant createdAt;
    private Instant updatedAt;

    public UserResponse(UserMasterEntity entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.email = entity.getEmail();
        this.isActive = entity.isActive();
        this.role = entity.getRole();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
