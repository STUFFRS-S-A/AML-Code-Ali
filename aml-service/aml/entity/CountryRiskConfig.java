package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "country_risk_config")
@Data
@Entity
public class CountryRiskConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String RiskScoreNationality;
    private String RiskScoreGeography;
}
