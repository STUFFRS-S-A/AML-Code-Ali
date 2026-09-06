package com.appopay.visa.controller;

import com.appopay.visa.model.*;
import com.appopay.visa.service.PanService;
import com.appopay.visa.service.VisaAuthAndEncryptionServicesImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("card/")
public class CardManagementController {

    private static final Logger log = LoggerFactory.getLogger(CardManagementController.class);

    @Autowired
    private VisaAuthAndEncryptionServicesImpl visaAuthAndEncryptionServices;

    @Autowired
    private PanService panService;

    @PostMapping(value = "newCard")
    public ResponseEntity<NewCardResponseDTO> issueNewCard(@RequestBody NewCardRequestDTO request) throws IOException {
        File jsonFile = ResourceUtils.getFile("classpath:static/NewCardResponse.json");

        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Convert JSON file to DTO
        NewCardResponseDTO response = mapper.readValue(jsonFile, NewCardResponseDTO.class);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "cardEnquiry")
    public ResponseEntity<CardEnquiryResponseDTO> cardEnquiry(@RequestBody CardEnquiryRequestDTO request) throws IOException {
        File jsonFile = ResourceUtils.getFile("classpath:static/CardEnquiryResponse.json");

        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Convert JSON file to DTO
        CardEnquiryResponseDTO response = mapper.readValue(jsonFile, CardEnquiryResponseDTO.class);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "cardUpdate")
    public ResponseEntity<CardUpdateResponseDTO> cardUpdate(@RequestBody CardUpdateRequestDTO request) throws IOException {
        File jsonFile = ResourceUtils.getFile("classpath:static/CardUpdateResponse.json");

        // Create ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();

        // Convert JSON file to DTO
        CardUpdateResponseDTO response = mapper.readValue(jsonFile, CardUpdateResponseDTO.class);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "generatePan")
    public ResponseEntity<String> generatePan() throws Exception {
        String pan = panService.generatePan();
        return ResponseEntity.ok(pan);
    }



}