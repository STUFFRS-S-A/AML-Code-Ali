package com.appopay.visa.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeviceVerifyPinRequestDTO {
    private String deviceId;

    @Size(min = 6, max = 255)
    private String mobilePin;
}
