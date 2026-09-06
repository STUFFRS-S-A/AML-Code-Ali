package com.appopay.aml.repository.rule;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.ruleConfig.ConditionLogic;
import com.appopay.aml.entity.ruleConfig.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConditionLogicRepository extends JpaRepository<ConditionLogic, Long> {

    ConditionLogic findByRuleId(Rule rule);

    boolean existsByRuleId(Rule rule);

    @Transactional
    void deleteByRuleId(Rule rule);
}
