package com.appopay.visa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "devices")
@Data
@Entity
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String deviceId;
    private String status;
    private String mobilePin;
    private String mobileNo;
    private String previousPins;
    private Instant lastLoginTime;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
