package com.appopay.aml.model.customer;

import lombok.Data;

@Data
public class ValidateRiskResV2DTO {

    private boolean newCreated;
    private String customerId;
    private boolean signupAllowed;

    public ValidateRiskResV2DTO(boolean newCreated, String customerId, boolean signupAllowed) {
        this.newCreated = newCreated;
        this.customerId = customerId;
        this.signupAllowed = signupAllowed;
    }

    public ValidateRiskResV2DTO() {
    }
}
