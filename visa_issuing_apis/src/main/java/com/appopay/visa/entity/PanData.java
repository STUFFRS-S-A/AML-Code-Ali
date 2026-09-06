package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@Table(name = "pandata")
@Data
@Entity
public class PanData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bin;
    private Long productCode;

    private Long RandomNumber;

    private Instant createdAt;

    private Instant updatedAt;

}
