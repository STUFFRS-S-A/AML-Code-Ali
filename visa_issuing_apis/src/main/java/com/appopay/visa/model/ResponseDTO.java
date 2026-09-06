package com.appopay.visa.model;

import lombok.Data;

@Data
public class ResponseDTO {
    private String status = "200";
    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }
}
