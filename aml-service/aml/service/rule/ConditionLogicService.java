package com.appopay.aml.service.rule;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.ruleConfig.ConditionLogic;
import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.model.rule.PaginatedConditionLogic;
import com.appopay.aml.repository.rule.ConditionLogicRepository;
import com.appopay.aml.repository.rule.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConditionLogicService {

    private static final Logger log = LoggerFactory.getLogger(ConditionLogicService.class);

    @Autowired
    private ConditionLogicRepository conditionLogicRepository;

    @Autowired
    private RuleRepository ruleRepository;

    public ConditionLogic getById(Long id) {
        Optional<ConditionLogic> conditionLogic = conditionLogicRepository.findById(id);
        if (conditionLogic.isPresent()) {
            return conditionLogic.get();
        }
        throw new CustomException("Condition Logic not found");
    }

    public ConditionLogic create(long ruleId, boolean allRequired) {
        Optional<Rule> optionalRule = ruleRepository.findById(ruleId);
        if (optionalRule.isEmpty()) throw new CustomException("Rule not found");
        Rule rule = optionalRule.get();
        ConditionLogic conditionLogic = conditionLogicRepository.findByRuleId(rule);

        if (conditionLogic == null) {
            conditionLogic = new ConditionLogic();
            conditionLogic.setAllRequired(allRequired);
            conditionLogic.setRuleId(rule);
        } else {
            conditionLogic.setAllRequired(allRequired);
        }
        return conditionLogicRepository.save(conditionLogic);
    }

    public PaginatedConditionLogic findAll(Pageable pageable) {
        PaginatedConditionLogic response = new PaginatedConditionLogic();
        response.setData(conditionLogicRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(conditionLogicRepository.count());
        return response;
    }

    @Transactional
    public void deleteEntityByRuleId(Rule rule) {
        if (conditionLogicRepository.existsByRuleId(rule)) {
            conditionLogicRepository.deleteByRuleId(rule);
        }
    }

}
