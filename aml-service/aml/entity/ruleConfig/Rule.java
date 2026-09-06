package com.appopay.aml.entity.ruleConfig;

import com.appopay.aml.entity.Person;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "rule")
@Data
@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private String type;
    private String target;
    private boolean isActive;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id")
    private List<RuleConditions> ruleConditions;

}