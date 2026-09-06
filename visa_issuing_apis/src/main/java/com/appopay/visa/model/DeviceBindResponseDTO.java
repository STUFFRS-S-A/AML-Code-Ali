package com.appopay.visa.model;

import lombok.Data;

@Data
public class DeviceBindResponseDTO {
    private String message = "ok";
    private String previousDeviceId;

    public DeviceBindResponseDTO(String previousDeviceId) {
        this.previousDeviceId = previousDeviceId;
    }
}
