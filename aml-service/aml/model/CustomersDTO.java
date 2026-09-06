package com.appopay.aml.model;

import com.appopay.aml.util.RiskStatus;
import lombok.Data;

@Data
public class CustomersDTO {

    String id;
    String customerName;
    String countryOfOrigin;
    RiskStatus riskStatus;
    String riskScore;
    Boolean politicallyExposedPerson;
    Boolean isBlocked;
    String identityType;
    String identityNumber;
    String sourceOfIncome;
    String phoneNumber;

}
