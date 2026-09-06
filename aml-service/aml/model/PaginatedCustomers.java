package com.appopay.aml.model;

import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.IAM;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedCustomers {

    List<Customers> data;
    Long totalDocuments;

}
