package com.appopay.aml.repository.rule;

import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.entity.ruleConfig.RuleConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleConditionsRepository extends JpaRepository<RuleConditions, Long> {

}
