package com.appopay.aml.model;

import com.appopay.aml.entity.MPADetails;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.entity.MerchantPEPFamily;
import com.appopay.aml.util.RiskStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MPADetailsDTO {

    long mpaId;
    String businessName;
    String businessBranch;
    String businessBranchName;
    String businessCity;
    String businessState;
    String businessZipCode;
    String businessPhoneNo;
    String businessWebsite;
    String boName;
    String boId;
    String boIdExpiryDate;
    String boIdGeneratedBy;
    String boAddress;
    String boCity;
    String boState;
    String boZipCode;
    String boPhoneNo;
    String programDescription;
    String agentSignature;
    String agentSignatureDate;
    String fmTmSignature;
    String fmTmSignatureDate;
    double noOfCards;
    double costPerCard;
    double initialCardBalance;
    String setResources;
    double monthlyCharge;
    double transactionFee;
    double depositOnAccount;
    double tradeCharge;
    double ltyPntIssFee;
    String redemptionOfPnts;
    double posTerminalFee;
    double virtualTerminalFee;
    double mobileTerminalFee;
    String appopayMobAccType;
    String businessAccType;
    String appopayAccType;
    String nameOnAcc;
    String bankName;
    String branchName;
    String accountName;
    String phoneNumber;
    String accountManager;
    double depAmtPerOpening;
    String apprLineOfCdtr;

    public MPADetails toEntity() {
        MPADetails mpaDetails = new MPADetails();
        mpaDetails.setBusinessName(this.businessName);
        mpaDetails.setBusinessBranch(this.businessBranch);
        mpaDetails.setBusinessBranchName(this.businessBranchName);
        mpaDetails.setBusinessCity(this.businessCity);
        mpaDetails.setBusinessState(this.businessState);
        mpaDetails.setBusinessZipCode(this.businessZipCode);
        mpaDetails.setBusinessPhoneNo(this.businessPhoneNo);
        mpaDetails.setBusinessWebsite(this.businessWebsite);
        mpaDetails.setBoName(this.boName);
        mpaDetails.setBoId(this.boId);
        mpaDetails.setBoIdExpiryDate(this.boIdExpiryDate);
        mpaDetails.setBoIdGeneratedBy(this.boIdGeneratedBy);
        mpaDetails.setBoAddress(this.boAddress);
        mpaDetails.setBoCity(this.boCity);
        mpaDetails.setBoState(this.boState);
        mpaDetails.setBoZipCode(this.boZipCode);
        mpaDetails.setBoPhoneNo(this.boPhoneNo);
        mpaDetails.setProgramDescription(this.programDescription);
        mpaDetails.setAgentSignature(this.agentSignature);
        mpaDetails.setAgentSignatureDate(this.agentSignatureDate);
        mpaDetails.setFmTmSignature(this.fmTmSignature);
        mpaDetails.setFmTmSignatureDate(this.fmTmSignatureDate);
        mpaDetails.setNoOfCards(this.noOfCards);
        mpaDetails.setCostPerCard(this.costPerCard);
        mpaDetails.setInitialCardBalance(this.initialCardBalance);
        mpaDetails.setSetResources(this.setResources);
        mpaDetails.setMonthlyCharge(this.monthlyCharge);
        mpaDetails.setTransactionFee(this.transactionFee);
        mpaDetails.setDepositOnAccount(this.depositOnAccount);
        mpaDetails.setTradeCharge(this.tradeCharge);
        mpaDetails.setLtyPntIssFee(this.ltyPntIssFee);
        mpaDetails.setRedemptionOfPnts(this.redemptionOfPnts);
        mpaDetails.setPosTerminalFee(this.posTerminalFee);
        mpaDetails.setVirtualTerminalFee(this.virtualTerminalFee);
        mpaDetails.setMobileTerminalFee(this.mobileTerminalFee);
        mpaDetails.setAppopayMobAccType(this.appopayMobAccType);
        mpaDetails.setBusinessAccType(this.businessAccType);
        mpaDetails.setAppopayAccType(this.appopayAccType);
        mpaDetails.setNameOnAcc(this.nameOnAcc);
        mpaDetails.setBankName(this.bankName);
        mpaDetails.setBranchName(this.branchName);
        mpaDetails.setAccountName(this.accountName);
        mpaDetails.setPhoneNumber(this.phoneNumber);
        mpaDetails.setAccountManager(this.accountManager);
        mpaDetails.setDepAmtPerOpening(this.depAmtPerOpening);
        mpaDetails.setApprLineOfCdtr(this.apprLineOfCdtr);

        return mpaDetails;
    }


}