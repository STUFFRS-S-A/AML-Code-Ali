package com.appopay.aml.entity;

import com.appopay.aml.model.CustomerTrxResDTO;
import com.appopay.aml.model.transaction.RecordCustomerTrxReqDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "transaction")
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String amount;
    private String type;
    private String description;
    private String merchantLocation;
    private boolean isAllowed;

    private boolean isFlagged;

    private String riskScore;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customers;


    public Transaction(RecordCustomerTrxReqDTO req, Customers customers, boolean isFlagged, String riskScore) {
        this.amount = req.getTransactionAmount();
        this.type = req.getTransactionType();
        this.description = req.getTransactionDescription();
        this.merchantLocation = req.getMerchantLocation();
        this.customers = customers;
        this.riskScore = riskScore;
        this.isAllowed = true;
        this.isFlagged = isFlagged;
    }

    public Transaction() {
    }

    public CustomerTrxResDTO toDTO() {
        CustomerTrxResDTO res = new CustomerTrxResDTO();
        res.setCustomerId(this.customers.getId().toString());
//        res.setCustomerName(this.customers.getCustomerName()); //
        res.setTransactionAmount(this.amount);
        res.setTransactionType(this.type);
        res.setTransactionDescription(this.description);
        res.setMerchantLocation(this.merchantLocation);
        res.setRiskScore(this.riskScore);
        res.setAllowed(this.isAllowed);

        return res;

    }
}
