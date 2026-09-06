package com.appopay.visa.Exception;


import lombok.Data;

@Data
public class ErrorResponse {
    private final String status;
    private final String message;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
