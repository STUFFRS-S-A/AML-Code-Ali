package com.appopay.aml.model;

import lombok.Data;

@Data
public class CustomerTrxResDTO {

//    private String customerName;
    private String transactionAmount;
    private String transactionType;
    private String transactionDescription;
    private String customerId;
    private String merchantLocation;
    private String riskScore;
    private boolean isAllowed;

}
