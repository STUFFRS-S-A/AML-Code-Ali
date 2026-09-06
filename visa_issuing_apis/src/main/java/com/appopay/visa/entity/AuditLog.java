package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "auditLog")
@Data
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rawLogs;

    private Instant createdAt;

    private Instant updatedAt;

}
