package com.appopay.aml.model.transaction;

import lombok.Data;

@Data
public class RecordCustomerTrxResDTO {

    private String riskScore;
    private boolean transactionAllowed;
    private boolean isFlagged;

    public RecordCustomerTrxResDTO(String riskScore, boolean isFlagged) {
        this.riskScore = riskScore;
        this.transactionAllowed = true;
        this.isFlagged= isFlagged;
    }
}
