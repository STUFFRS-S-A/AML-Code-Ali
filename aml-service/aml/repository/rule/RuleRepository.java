package com.appopay.aml.repository.rule;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.ruleConfig.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {


    List<Rule> findAllByIsActiveAndTargetIgnoreCase(boolean isActive,String transaction);
}
