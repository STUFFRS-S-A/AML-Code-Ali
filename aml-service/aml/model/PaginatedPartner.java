package com.appopay.aml.model;

import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Partner;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedPartner {

    List<Partner> data;
    Long totalDocuments;

}
