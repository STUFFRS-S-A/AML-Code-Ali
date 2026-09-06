package com.appopay.visa.controller;

import com.appopay.visa.model.*;
import com.appopay.visa.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("customer/")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    @PutMapping
    public ResponseEntity<CustomerDTO> updateOne(@RequestBody CustomerDTO customerDTO) {
        log.info("received update customer request: "+ customerDTO.toString());
        CustomerDTO response = customerService.updateOne(customerDTO);
        log.info("update customer response: "+ response.toString());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) {
        log.info("received create customer request: "+ customer.toString());
        CustomerDTO response = customerService.create(customer);
        log.info("create customer response: "+ response.toString());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") String id) {
        log.info("get customer request: "+ id.toString());
        CustomerDTO response = customerService.getById(id);
        log.info("get customer response: "+ response.toString());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/token")
    public  ResponseEntity<CustomerTokenDTO> upsert(@RequestBody CustomerTokenDTO req){
        log.info("token upsert customer request: "+ req.toString());
        CustomerTokenDTO response = customerService.upsert(req);
        log.info("token upsert customer response: "+ response.toString());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/token/{id}")
    public ResponseEntity<CustomerTokenDTO> getToken(@PathVariable String id){
        log.info("get customer token request: "+ id.toString());
        CustomerTokenDTO response = customerService.getToken(id);
        log.info("get customer token response: "+ response.toString());
        return ResponseEntity.ok().body(response);
    }

}
