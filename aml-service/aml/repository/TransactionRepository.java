package com.appopay.aml.repository;

import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCustomersAndCreatedAtAfter(Customers customer, Instant date);

    List<Transaction> findAllByCustomers(Customers customer);

    int countAllByCustomersAndCreatedAtBetween(Customers customer,Instant start, Instant end);
    List<Transaction> findAllByCustomersAndCreatedAtBetween(Customers customer,Instant start, Instant end);

}
