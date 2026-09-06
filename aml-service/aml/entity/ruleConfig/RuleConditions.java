package com.appopay.aml.entity.ruleConfig;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "rule_conditions")
@Data
@Entity
public class RuleConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String field; // ("trx-amt")
    private String checkConstraint; // (">,<,is not")\
    private String value;
}