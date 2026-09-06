package com.appopay.aml.model.rule;

import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.entity.ruleConfig.RuleConditions;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RuleDTO {
    Long id;
    String name;
    String description;
    String type;
    Boolean isActive;
    String target;
    @Valid
    List<RuleConditionsDTO> ruleConditionsDTO;

    public Rule toEntity() {
        Rule rule = new Rule();
        rule.setId(this.id);
        rule.setName(this.name);
        rule.setDescription(this.description);
        rule.setType(this.type);
        rule.setActive(this.isActive);
        rule.setTarget(this.target);
        List<RuleConditions> ruleConditions = new ArrayList<>();
        if (this.getRuleConditionsDTO() != null) {
            for (RuleConditionsDTO dto : this.getRuleConditionsDTO()) {
                ruleConditions.add(dto.toEntity());
            }
        }
        rule.setRuleConditions(ruleConditions);
        return rule;
    }
}