package com.appopay.aml.entity.ruleConfig;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "condition_logic")
@Data
@Entity
public class ConditionLogic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean allRequired;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id")
    private Rule ruleId;
}