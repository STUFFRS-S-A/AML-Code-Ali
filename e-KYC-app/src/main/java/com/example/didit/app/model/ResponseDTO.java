package com.example.didit.app.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ResponseDTO {
    private String status = "200"; //response code ; 00
    private String message;
    private String exceptionMsg;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDTO(String status, String message, String exceptionMsg) {
        this.status = status;
        this.message = message;
        this.exceptionMsg = exceptionMsg;
    }
}
