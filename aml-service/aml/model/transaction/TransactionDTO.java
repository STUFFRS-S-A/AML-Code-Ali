package com.appopay.aml.model.transaction;

import com.appopay.aml.entity.Transaction;
import lombok.Data;

@Data
public class TransactionDTO {

    Long id;
    String amount;
    String type;
    String description;
    String merchantLocation;
    Boolean isAllowed;
    Boolean isFlagged;
    String riskScore;

    //TODO ADD PHONE NO

    public Transaction toEntity() {
        Transaction transaction = new Transaction();
        transaction.setId(this.id);
        transaction.setAmount(this.amount);
        transaction.setType(this.type);
        transaction.setDescription(this.description);
        transaction.setAllowed(this.isAllowed);
        transaction.setFlagged(this.isFlagged);
        return transaction;
    }
}