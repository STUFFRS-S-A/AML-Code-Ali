package com.appopay.aml.controller;


import com.appopay.aml.entity.Merchant;
import com.appopay.aml.model.*;
import com.appopay.aml.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("merchant")
public class MerchantController {

    private static final Logger log = LoggerFactory.getLogger(MerchantController.class);
    @Autowired
    private MerchantService merchantService;

    @PostMapping(value = "/")
    public ResponseEntity<Merchant> createMerchant(@RequestBody MerchantDTO request) {
        log.info("creating merchant with name: " + request.getAccountName());
        return ResponseEntity.ok().body(merchantService.createMerchant(request));
    }

    @PutMapping(value = "/")
    public ResponseEntity<Merchant> updateOne(@RequestBody MerchantDTO request) {
        log.info("updating merchant with name: " + request.getAccountName());
        return ResponseEntity.ok().body(merchantService.updateOne(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Merchant> updateOne(@PathVariable("id") Long id) {
        log.info("retrieving merchant with id: " + id);
        return ResponseEntity.ok().body(merchantService.getById(id));
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedMerchant> getAllMerchants(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(merchantService.findAll(PageRequest.of(page, size)));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFile(@ModelAttribute() UploadDocumentDTO uploadDocumentDTO) {
        log.info("uploading file  with merchant id: " + uploadDocumentDTO.getId());
        return ResponseEntity.ok(merchantService.uploadDocuments(uploadDocumentDTO));
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> updateFile(@ModelAttribute() UploadDocumentDTO uploadDocumentDTO) {
        log.info("uploading file  with merchant id: " + uploadDocumentDTO.getId());
        return ResponseEntity.ok(merchantService.updateDocuments(uploadDocumentDTO));
    }

    @DeleteMapping("/deleteFile/{fileName}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileName) {
        merchantService.deleteFile(fileName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/clearField/{id}")
    public ResponseEntity<Void> clearField(@PathVariable Long id, @RequestParam DeleteOption fieldOption) {
        merchantService.clearField(fieldOption,id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable("id") long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/vip")
    public ResponseEntity<Merchant> updateFile(@RequestBody MPADetailsDTO mpaDetailsDTO) {
        log.info("uptating vip merchant with id: " + mpaDetailsDTO.getMpaId());
        return ResponseEntity.ok(merchantService.updateToVIP(mpaDetailsDTO));
    }


}
