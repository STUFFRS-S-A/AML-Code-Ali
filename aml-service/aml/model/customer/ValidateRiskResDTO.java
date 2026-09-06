package com.appopay.aml.model.customer;

import lombok.Data;

@Data
public class ValidateRiskResDTO {

    private String riskScore;
    private boolean signupAllowed;

    public ValidateRiskResDTO(String riskScore, boolean signupAllowed) {
        this.riskScore = riskScore;
        this.signupAllowed = signupAllowed;
    }

    public ValidateRiskResDTO() {
    }
}
