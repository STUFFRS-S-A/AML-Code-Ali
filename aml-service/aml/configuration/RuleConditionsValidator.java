package com.appopay.aml.configuration;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.model.rule.RuleConditionsDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RuleConditionsValidator implements ConstraintValidator<ValidRuleConditions, RuleConditionsDTO> {


    @Override
    public boolean isValid(RuleConditionsDTO dto, ConstraintValidatorContext context) {
        // Ensure field, checkConstraint, and value are not null
        if (dto.getField() == null || dto.getCheckConstraint() == null || dto.getValue() == null) {
            return false;
        }

        // Conditional validation logic based on 'field'
        switch (dto.getField()) {
            case "TRANSACTION_FREQUENCY":
                return validateTransactionFrequency(dto, context);
            case "IP_LOCATION":
                return validateIpLocation(dto, context);
            case "PEP":
            case "OCCUPATION":
            case "LOCATION":
                return validtateCustomerRule(dto, context);
            // Add more cases as needed for other fields
            default:
                throw new CustomException("invalid input");
        }
    }

    private boolean validateTransactionFrequency(RuleConditionsDTO dto, ConstraintValidatorContext context) {
        // checkConstraint must be GREATER_THAN or LESS_THAN
        if (!dto.getCheckConstraint().equals("GREATER_THAN") && !dto.getCheckConstraint().equals("LESS_THAN")) {
            throw new CustomException("For TRANSACTION_FREQUENCY, checkConstraint must be GREATER_THAN or LESS_THAN");
//            return false;
        }

        // value must be numeric
        try {
            Integer.parseInt(dto.getValue());
        } catch (NumberFormatException e) {
            throw new CustomException("For TRANSACTION_FREQUENCY, value must be a number");
//            return false;
        }

        return true;
    }

    private boolean validateIpLocation(RuleConditionsDTO dto, ConstraintValidatorContext context) {
        // Example: Check constraint can be "EQUALS" or "NOT_EQUALS" for IP_LOCATION
        if (!dto.getCheckConstraint().equals("EQUALS") && !dto.getCheckConstraint().equals("NOT_EQUALS")) {
            throw new CustomException("For IP_LOCATION, checkConstraint must be EQUALS or NOT_EQUALS");
//            return false;
        }

        // value should be an IP address (basic regex validation)
        if (!dto.getValue().matches("^([0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            throw new CustomException("For IP_LOCATION, value must be a valid IP address");
//            return false;
        }

        return true;
    }

    private boolean validtateCustomerRule(RuleConditionsDTO dto, ConstraintValidatorContext context) {
        // Example: Check constraint can be "EQUALS" or "NOT_EQUALS" for IP_LOCATION
        if (!dto.getCheckConstraint().equals("NOT_ALLOWED")) {
            throw new CustomException("For PEP or OCCUPATION or LOCATION constraint must be NOT_ALLOWED");
//            return false;
        }
        return true;
    }

//    private void validtateCustomerRule(context, String message) {
//        throw new CustomException(message);
//    }
}
