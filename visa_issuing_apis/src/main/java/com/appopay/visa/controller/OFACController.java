package com.appopay.visa.controller;

import com.appopay.visa.model.ChangeOfacRequestDTO;
import com.appopay.visa.model.PaginatedOfacDto;
import com.appopay.visa.model.ResponseDTO;
import com.appopay.visa.model.VisaApiResponse;
import com.appopay.visa.service.OFACService;
import com.appopay.visa.service.VisaAuthAndEncryptionServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("visa/ofac/")
public class OFACController {

    private static final Logger log = LoggerFactory.getLogger(OFACController.class);

    @Autowired
    private OFACService ofacService;

    @GetMapping("{page}/{size}")
    public ResponseEntity<PaginatedOfacDto> getAll(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return ResponseEntity.ok().body(ofacService.getAll(PageRequest.of(page,size)));
    }

    @GetMapping(value = "status/{name}")
    public ResponseEntity<ResponseDTO> getStatus(@PathVariable String name) throws Exception {
        log.info("getting OFAC status of: " + name);
        String status =ofacService.getStatus(name);
        log.info("OFAC get status response: " + status);
        return ResponseEntity.ok().body(new ResponseDTO(status));
    }

    @PutMapping(value = "status/{name}")
    public ResponseEntity<ResponseDTO> changeStatus(@RequestBody ChangeOfacRequestDTO request) throws Exception {
        log.info("changing OFAC status of: " + request.toString());
        String status =ofacService.changeStatus(request);
        log.info("OFAC change status response: " + status);
        return ResponseEntity.ok().body(new ResponseDTO(status));
    }


}