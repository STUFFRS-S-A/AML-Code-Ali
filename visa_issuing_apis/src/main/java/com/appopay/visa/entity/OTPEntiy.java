package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "otp")
@Data
@Entity
public class OTPEntiy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String otp;
    private boolean isValid;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public OTPEntiy(String number, String otp, boolean isValid) {
        this.number = number;
        this.otp = otp;
        this.isValid = isValid;
    }

    public OTPEntiy() {

    }
}
