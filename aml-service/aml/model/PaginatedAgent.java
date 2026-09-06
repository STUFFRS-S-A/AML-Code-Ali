package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Customers;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedAgent {

    List<Agent> data;
    Long totalDocuments;

}
