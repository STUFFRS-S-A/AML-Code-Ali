package com.appopay.aml.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDocumentDTO {

    long id;
    MultipartFile frontId;
    MultipartFile backId;
    MultipartFile compRegistration;
    MultipartFile license;
    MultipartFile others1;
    MultipartFile others2;

}
