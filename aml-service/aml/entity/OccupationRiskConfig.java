package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "occupation_risk_config")
@Data
@Entity
public class OccupationRiskConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String occupation;
    private String RiskScore;
}
