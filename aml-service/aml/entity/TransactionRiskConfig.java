package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "transaction_risk_config")
@Data
@Entity
public class TransactionRiskConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long dailyLimit;
    private Long monthlyFrequency;
    private Long monthlyLimit;
    private String locationFrequency;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
