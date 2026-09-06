package com.appopay.visa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.List;

@Table(name = "kyc_info")
@Data
@Entity
public class KYCInfo {
    @Id
    private String sessionId;

    private String status;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
