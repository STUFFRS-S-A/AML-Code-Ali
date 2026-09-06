package com.appopay.aml.entity;

import com.appopay.aml.util.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Table(name = "mpa_details")
@Data
@Entity
public class MPADetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String businessBranch;
    private String businessBranchName;
    private String businessCity;
    private String businessState;
    private String businessZipCode;
    private String businessPhoneNo;
    private String businessWebsite;
    private String boName;
    private String boId;
    private String boIdExpiryDate;
    private String boIdGeneratedBy;
    private String boAddress;
    private String boCity;
    private String boState;
    private String boZipCode;
    private String boPhoneNo;
    private String programDescription;
    private String agentSignature;
    private String agentSignatureDate;
    private String fmTmSignature;
    private String fmTmSignatureDate;
    private double noOfCards;
    private double costPerCard;
    private double initialCardBalance;
    private String setResources;
    private double monthlyCharge;
    private double transactionFee;
    private double depositOnAccount;
    private double tradeCharge;
    private double ltyPntIssFee;
    private String redemptionOfPnts;
    private double posTerminalFee;
    private double virtualTerminalFee;
    private double mobileTerminalFee;
    private String appopayMobAccType;
    private String businessAccType;
    private String appopayAccType;
    private String nameOnAcc;
    private String bankName;
    private String branchName;
    private String accountName;
    private String phoneNumber;
    private String accountManager;
    private double depAmtPerOpening;
    private String apprLineOfCdtr;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;



}
