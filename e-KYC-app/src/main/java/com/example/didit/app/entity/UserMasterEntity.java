package com.example.didit.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.Instant;

@Table(name = "user_master")
@Data
@Entity
public class UserMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String role;

    private String password;

    private String email;

    private boolean isActive;

    @CreatedBy
    private Long createdBy;

    @CreationTimestamp
    private Instant createdAt;

    @LastModifiedBy
    private Long updatedBy;

    @UpdateTimestamp
    private Instant updatedAt;

}
