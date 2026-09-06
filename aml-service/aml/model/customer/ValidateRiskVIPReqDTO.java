package com.appopay.aml.model.customer;

import lombok.Data;

@Data
public class ValidateRiskVIPReqDTO {

    private String customerName;
    private String countryOfOrigin;
    private boolean politicallyExposedPerson;
    private String sourceOfIncome;
    private String customerId;

}
