package com.appopay.aml.model;

import com.appopay.aml.entity.IAM;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedUsers {

    List<IAM> data;
    Long totalDocuments;

}
