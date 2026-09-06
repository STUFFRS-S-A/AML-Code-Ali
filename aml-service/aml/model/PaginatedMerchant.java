package com.appopay.aml.model;

import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Merchant;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedMerchant {

    List<Merchant> data;
    Long totalDocuments;

}
