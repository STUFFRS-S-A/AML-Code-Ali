package com.appopay.aml.configuration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // Applies at the class level
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RuleConditionsValidator.class)
public @interface ValidRuleConditions {

    String message() default "Invalid rule conditions";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
