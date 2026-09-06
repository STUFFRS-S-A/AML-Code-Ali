package com.appopay.aml.util;

public enum RiskStatus {
    LOW("5"),
    MEDIUM("10"),
    HIGH("15");

    private final String value;

    RiskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
