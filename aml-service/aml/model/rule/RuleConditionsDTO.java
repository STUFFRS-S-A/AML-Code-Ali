package com.appopay.aml.model.rule;

import com.appopay.aml.configuration.ValidRuleConditions;
import com.appopay.aml.entity.ruleConfig.RuleConditions;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidRuleConditions
public class RuleConditionsDTO {

    Long id;
    @NotNull(message = "Field is required")
    String field;
    @NotNull(message = "checkConstraint is required")
    String checkConstraint;
    @NotNull(message = "value is required")
    String value;

    public RuleConditions toEntity() {
        RuleConditions ruleConditions = new RuleConditions();
        ruleConditions.setId(this.id);
        ruleConditions.setField(this.field);
        ruleConditions.setCheckConstraint(this.checkConstraint);
        ruleConditions.setValue(this.value);
        return ruleConditions;
    }
}