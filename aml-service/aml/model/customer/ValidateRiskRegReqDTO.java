package com.appopay.aml.model.customer;

import lombok.Data;

@Data
public class ValidateRiskRegReqDTO {

    private String name;
    private String countryOfOrigin;
    private boolean politicallyExposedPerson;
    private String id;
    private String identityType;
    private String identityNumber;
    private String phoneNumber;


}
