package com.appopay.aml.model.transaction;

import lombok.Data;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Data
public class TransactionValidatorModel {
    String amount;
    String frequency;
    String ipLocation;
    String status;
    String deviceId;

}
