package com.appopay.aml.model;

import lombok.Data;

@Data
public class CustomerAmlResDTO {

    private String riskScore;

    private boolean isBlocked;

}
