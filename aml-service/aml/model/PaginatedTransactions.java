package com.appopay.aml.model;

import com.appopay.aml.entity.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedTransactions {

    List<Transaction> data;
    Long documents;

}
