package com.appopay.visa.controller;

import com.appopay.visa.model.NewCardResponseDTO;
import com.appopay.visa.model.VisaApiResponse;
import com.appopay.visa.service.VisaAuthAndEncryptionServicesImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("visa/")
public class VisaController {

    private static final Logger log = LoggerFactory.getLogger(VisaController.class);

    @Autowired
    private VisaAuthAndEncryptionServicesImpl visaAuthAndEncryptionServices;

    @PostMapping(value = "getJWSToken")
    public VisaApiResponse getJWSToken(@RequestHeader("requestPath") String requestPath,
                                       @RequestBody String visaRequest) {
        return visaAuthAndEncryptionServices.getJWSToken(requestPath, visaRequest);
    }

    @PostMapping(value = "getJWEToken")
    public VisaApiResponse getJWEToken(@RequestBody String payload) {
        return visaAuthAndEncryptionServices.generateJweToken(payload);
    }


}