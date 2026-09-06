package com.appopay.aml.service.rule;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.entity.ruleConfig.RuleConditions;
import com.appopay.aml.model.customer.ValidateRiskRegReqDTO;
import com.appopay.aml.model.rule.PaginatedRule;
import com.appopay.aml.model.rule.RuleDTO;
import com.appopay.aml.model.transaction.TransactionValidatorModel;
import com.appopay.aml.repository.TransactionRepository;
import com.appopay.aml.repository.rule.ConditionLogicRepository;
import com.appopay.aml.repository.rule.RuleRepository;
import com.appopay.aml.rest.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RuleService {

    private static final Logger log = LoggerFactory.getLogger(RuleService.class);

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ConditionLogicRepository conditionLogicRepository;

    @Autowired
    private ConditionLogicService conditionLogicService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestClient restClient;

    public Rule getById(Long id) {
        Optional<Rule> rule = ruleRepository.findById(id);
        if (rule.isPresent()) {
            return rule.get();
        }
        throw new CustomException("Rule not found");
    }

    @Transactional
    public void deleteEntityById(Long id) {
        if (ruleRepository.existsById(id)) {
            conditionLogicService.deleteEntityByRuleId(ruleRepository.findById(id).get());
            ruleRepository.deleteById(id);
        } else {
            throw new CustomException("Entity with ID " + id + " not found.");
        }
    }

    public PaginatedRule findAll(Pageable pageable) {
        PaginatedRule response = new PaginatedRule();
        response.setData(ruleRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(ruleRepository.count());
        return response;
    }

    public Rule create(RuleDTO ruleDTO) {
        if (ruleDTO.getId() != null) {
            throw new CustomException("new rule can not have ID");
        } else {
            return ruleRepository.save(ruleDTO.toEntity());
        }
    }

    public Rule activate(long id, boolean isActive) {
        Optional<Rule> optionalRule = ruleRepository.findById(id);
        if (optionalRule.isEmpty()) {
            throw new CustomException("rule not found");
        }
        Rule rule = optionalRule.get();

        rule.setActive(isActive);
        return ruleRepository.save(rule);

    }

    public boolean checkValidity(TransactionValidatorModel req, Customers customers) {
        List<Rule> rules = ruleRepository.findAllByIsActiveAndTargetIgnoreCase(true, "Transaction");
        boolean isValid = true;
        for (Rule rule : rules) {
            long checkCount = getFlagCount(req, rule.getRuleConditions(), customers);
            boolean allRequired = conditionLogicRepository.findByRuleId(rule).isAllRequired();
            if (allRequired && checkCount == rule.getRuleConditions().size()) isValid = false;
            else if (!allRequired && checkCount > 0) {
                isValid = false;
            }
        }
        return isValid;
    }

    public long getFlagCount(TransactionValidatorModel req, List<RuleConditions> ruleConditionsList, Customers customer) {
        long checkCount = 0;
        for (RuleConditions ruleCondition : ruleConditionsList) {
            if (ruleCondition.getField().equals("TRANSACTION_FREQUENCY")) {
                String[] parts = ruleCondition.getValue().split(",");
                int number = Integer.parseInt(parts[0]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
                LocalDateTime startDate = LocalDateTime.parse(parts[1], formatter);
                LocalDateTime endDate = LocalDateTime.parse(parts[2], formatter);
                Instant start = startDate.toInstant(ZoneOffset.UTC);
                Instant end = endDate.toInstant(ZoneOffset.UTC);
                Instant now = Instant.now();
                int count = transactionRepository.countAllByCustomersAndCreatedAtBetween(customer, start, end);
                if (end.isAfter(now)) count++;
                if (ruleCondition.getCheckConstraint().equals("GREATER_THAN")) {
                    if (count > number) checkCount++;
                } else if (ruleCondition.getCheckConstraint().equals("EQUAL_TO")) {
                    if (count >= number) checkCount++;
                }
            } else if (ruleCondition.getField().equals("TRANSACTION_AMOUNT")) {
                if (ruleCondition.getCheckConstraint().equals("GREATER_THAN")) {
                    if (Long.parseLong(req.getAmount()) > Long.parseLong(ruleCondition.getValue())) checkCount++;
                } else if (ruleCondition.getCheckConstraint().equals("LESS_THAN")) {
                    if (Long.parseLong(req.getAmount()) < Long.parseLong(ruleCondition.getValue())) checkCount++;
                } else if (ruleCondition.getCheckConstraint().equals("EQUALS")) {
                    if (Long.parseLong(req.getAmount()) == Long.parseLong(ruleCondition.getValue())) checkCount++;
                }
            } else if (ruleCondition.getField().equals("DAILY_TRANSACTION_FREQUENCY")) {
                Instant now = Instant.now();
                ZonedDateTime startOfDayZoned = now.atZone(ZoneOffset.UTC).toLocalDate().atStartOfDay(ZoneOffset.UTC);
                Instant startOfDay = startOfDayZoned.toInstant();
                ZonedDateTime endOfDayZoned = startOfDayZoned.plusDays(1).minusNanos(1);
                Instant endOfDay = endOfDayZoned.toInstant();

                long transactionCount = transactionRepository.countAllByCustomersAndCreatedAtBetween(customer, startOfDay, endOfDay);
                if (transactionCount + 1 > Long.parseLong(ruleCondition.getValue())) checkCount++;
            } else if (ruleCondition.getField().equals("DAILY_TRANSACTION_AMOUNT")) {
                Instant now = Instant.now();
                ZonedDateTime startOfDayZoned = now.atZone(ZoneOffset.UTC).toLocalDate().atStartOfDay(ZoneOffset.UTC);
                Instant startOfDay = startOfDayZoned.toInstant();
                ZonedDateTime endOfDayZoned = startOfDayZoned.plusDays(1).minusNanos(1);
                Instant endOfDay = endOfDayZoned.toInstant();

                List<Transaction> list = transactionRepository.findAllByCustomersAndCreatedAtBetween(customer, startOfDay, endOfDay);
                long amount = list.stream().map(Transaction::getAmount).mapToLong(Long::parseLong).sum();
                if (Long.parseLong(req.getAmount()) + amount > Long.parseLong(ruleCondition.getValue())) checkCount++;
            } else if (ruleCondition.getField().equals("IP_LOCATION")) {
                if (ruleCondition.getCheckConstraint().equals("NOT_ALLOWED")) {
                    if (req.getIpLocation().equals(ruleCondition.getValue())) checkCount++;
                }
            } else if (ruleCondition.getField().equals("ACCOUNT_STATUS")) {
                if (ruleCondition.getCheckConstraint().equals("NOT_ALLOWED")) {
                    if (req.getStatus().equals(ruleCondition.getValue())) checkCount++;
                }
            } else if (ruleCondition.getField().equals("DEVICE_ID")) {
                if (ruleCondition.getValue().equals("ACTIVE")) {
                    String message = restClient.checkStatus(customer.getPhoneNumber(), req.getDeviceId());
                    if (!message.equals("ACTIVE")) checkCount++;
                }
            }
        }
        return checkCount;
    }

    public boolean checkValidity(ValidateRiskRegReqDTO req) {
        List<Rule> rules = ruleRepository.findAllByIsActiveAndTargetIgnoreCase(true, "Customer");
        boolean isValid = true;
        for (Rule rule : rules) {
            long checkCount = getFlagCount(req, rule.getRuleConditions());
            boolean allRequired = conditionLogicRepository.findByRuleId(rule).isAllRequired();
            if (allRequired && checkCount == rule.getRuleConditions().size()) isValid = false;
            else if (!allRequired && checkCount > 0) {
                isValid = false;
            }
        }
        return isValid;
    }

    public long getFlagCount(ValidateRiskRegReqDTO req, List<RuleConditions> ruleConditionsList) {
        long checkCount = 0;
        for (RuleConditions ruleCondition : ruleConditionsList) {

            if (ruleCondition.getField().equals("PEP")) {
                if (ruleCondition.getCheckConstraint().equals("NOT_ALLOWED")) {
                    if (req.isPoliticallyExposedPerson()) checkCount++;
                }
            }

            if (ruleCondition.getField().equals("OCCUPATION")) {
//                if (ruleCondition.getCheckConstraint().equals("NOT_ALLOWED")) {
//                    if (req.getOccupation().equals(ruleCondition.getValue())) checkCount++;
//                }
            }

            if (ruleCondition.getField().equals("LOCATION")) {
                if (ruleCondition.getCheckConstraint().equals("NOT_ALLOWED")) {
                    if (req.getCountryOfOrigin().equals(ruleCondition.getValue())) checkCount++;
                }
            }

        }
        return checkCount;
    }


}
