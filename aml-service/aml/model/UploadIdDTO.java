package com.appopay.aml.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadIdDTO {

    String customerId;
    String firstName;
    String lastName;
    String expiryDate;
    MultipartFile front;
    MultipartFile back;

}
