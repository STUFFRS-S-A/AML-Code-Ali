package com.example.didit.app.entity;

import jakarta.persistence.*;
import lombok.Data;

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
