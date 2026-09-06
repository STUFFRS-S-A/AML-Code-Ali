package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.model.PaginatedTransactions;
import com.appopay.aml.model.transaction.TransactionDTO;
import com.appopay.aml.model.transaction.RecordCustomerTrxReqDTO;
import com.appopay.aml.model.transaction.RecordCustomerTrxResDTO;
import com.appopay.aml.model.transaction.TransactionValidatorModel;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.CustomerRepository;
import com.appopay.aml.repository.TransactionRepository;
import com.appopay.aml.repository.TransactionRiskConfigRepository;
import com.appopay.aml.service.rule.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRiskConfigRepository transactionRiskConfigRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;
    @Autowired
    private RuleService ruleService;


//    public RecordCustomerTrxResDTO recordTrx(RecordCustomerTrxReqDTO req) {
//        log.info("transaction received with cid " + req.getCustomerId());
//        boolean isAllowed = true;
//        Optional<Customers> optionalCustomers = customerRepository.findById(req.getCustomerId());
//        Customers customers = null;
//        if (optionalCustomers.isPresent())
//            customers = optionalCustomers.get();
//        else {
//            throw new CustomException("customer not found");
//        }
//        TransactionRiskConfig transactionRiskConfig = transactionRiskConfigRepository.findAll().get(0);
//        LocalDate now = LocalDate.now();
//        Instant startOfMonth = now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
//        Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
//        List<Transaction> monthlyTransactions = transactionRepository.findAllByCustomersAndCreatedAtAfter(customers, startOfMonth);
//        List<Transaction> dailyTransactions = transactionRepository.findAllByCustomersAndCreatedAtAfter(customers, startOfToday);
//
//        Long monthlyAmount = monthlyTransactions.stream().mapToLong(n -> Long.parseLong(n.getAmount())).sum();
//        Long dailyAmount = dailyTransactions.stream().mapToLong(n -> Long.parseLong(n.getAmount())).sum();
//
//        CountryRiskConfig merchantCountryScore = countryRiskConfigRepository.findByCountryIgnoreCase(req.getMerchantLocation());
//        Long riskScore = 0L;
//        if (merchantCountryScore != null)
//            riskScore = Long.valueOf(merchantCountryScore.getRiskScoreGeography());
//        if (monthlyAmount + Long.parseLong(req.getTransactionAmount()) > transactionRiskConfig.getMonthlyLimit())
//            isAllowed = false;
//
//        else if (dailyAmount + Long.parseLong(req.getTransactionAmount()) > transactionRiskConfig.getDailyLimit())
//            isAllowed = false;
//
//        else if (monthlyTransactions.size() + 1 > transactionRiskConfig.getMonthlyFrequency())
//            isAllowed = false;
//
//        else if (!req.getMerchantLocation().equals(customers.getCountryOfOrigin()))
//            isAllowed = false;
//
//        else if (riskScore > Constants.ALLOWED_RISK)
//            isAllowed = false;
//
//
//        boolean isFlagged = !isAllowed;
//        Transaction transaction = new Transaction(req, customers, isFlagged, riskScore.toString());
//
//        transactionRepository.save(transaction);
//
//        return new RecordCustomerTrxResDTO(riskScore.toString(), true);
//    }

    public RecordCustomerTrxResDTO recordTrx(RecordCustomerTrxReqDTO req) {
        log.info("transaction received with cid " + req.getCustomerId());
        boolean isAllowed = true;
        Optional<Customers> optionalCustomers = customerRepository.findById(req.getCustomerId());
        Customers customers = null;
        if (optionalCustomers.isPresent()) customers = optionalCustomers.get();
        else {
            throw new CustomException("customer not found");
        }
        List<Transaction> customerTransactions = transactionRepository.findAllByCustomers(customers);
        TransactionValidatorModel transactionValidatorModel = new TransactionValidatorModel();
        transactionValidatorModel.setAmount(req.getTransactionAmount());
        transactionValidatorModel.setIpLocation(req.getMerchantLocation());
        transactionValidatorModel.setStatus(customers.isBlocked() ? "BLOCKED" : "NOT_BLOCKED");
        boolean isFlagged = !ruleService.checkValidity(transactionValidatorModel, customers);
        Transaction transaction = new Transaction(req, customers, isFlagged, null);
        transactionRepository.save(transaction);

        return new RecordCustomerTrxResDTO(null, isFlagged);
    }

    public PaginatedTransactions getAll(Pageable pageable) {
        PaginatedTransactions response = new PaginatedTransactions();
        response.setData(transactionRepository.findAll(pageable).stream().collect(Collectors.toList()));
        response.setDocuments(transactionRepository.count());
        return response;
    }

    public PaginatedTransactions getAllFlagged(Pageable pageable) {
        PaginatedTransactions response = new PaginatedTransactions();
        response.setData(transactionRepository.findAll(pageable).stream().filter(Transaction::isFlagged).collect(Collectors.toList()));
        response.setDocuments(transactionRepository.count());
        return response;
    }

    public Transaction updateOne(TransactionDTO req) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(req.getId());
        Transaction transaction = null;
        if (optionalTransaction.isEmpty()) {
            throw new CustomException("transaction not found");
        } else {
            transaction = optionalTransaction.get();
            if (req.getAmount() != null) transaction.setAmount(req.getAmount());
            if (req.getType() != null) transaction.setType(req.getType());
            if (req.getDescription() != null) transaction.setDescription(req.getDescription());
            if (req.getMerchantLocation() != null) transaction.setMerchantLocation(req.getMerchantLocation());
            if (req.getIsAllowed() != null) transaction.setAllowed(req.getIsAllowed());
            if (req.getIsFlagged() != null) transaction.setFlagged(req.getIsFlagged());
            if (req.getRiskScore() != null) transaction.setRiskScore(req.getRiskScore());
            return transactionRepository.save(transaction);

        }
    }
}
