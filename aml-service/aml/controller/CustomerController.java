package com.appopay.aml.controller;


import com.appopay.aml.entity.IdCard;
import com.appopay.aml.model.*;
import com.appopay.aml.model.customer.ValidateRiskRegReqDTO;
import com.appopay.aml.model.customer.ValidateRiskResDTO;
import com.appopay.aml.model.customer.ValidateRiskResV2DTO;
import com.appopay.aml.model.customer.ValidateRiskVIPReqDTO;
import com.appopay.aml.model.transaction.RecordCustomerTrxReqDTO;
import com.appopay.aml.model.transaction.RecordCustomerTrxResDTO;
import com.appopay.aml.service.CustomerService;
import com.appopay.aml.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @PutMapping
    public ResponseEntity<CustomersDTO> updateOne(@RequestBody CustomersDTO customers) {
        log.info("updating customer with id: "+ customers.getId());
        return ResponseEntity.ok().body(customerService.updateCustomer(customers));
    }

//    @PostMapping(value = "/validateRegularAcc")
//    public ResponseEntity<ValidateRiskResDTO> ValidateCustomerRiskProfileRegularAccount(@RequestBody ValidateRiskRegReqDTO request) {
//        log.info("creating customer with id: "+ request.getId());
//        return ResponseEntity.ok().body(customerService.validateRegAccount(request));
//    }

    @PostMapping(value = "/validateRegularAcc")
    public ResponseEntity<ValidateRiskResV2DTO> ValidateCustomerRiskProfileRegularAccount(@RequestBody ValidateRiskRegReqDTO request) {
        log.info("creating customer with id: "+ request.getId());
        return ResponseEntity.ok().body(customerService.validateRegAccount(request));
    }

    @PostMapping(value = "/validateVIPAcc")
    public ResponseEntity<ValidateRiskResDTO> ValidateCustomerRiskProfileVIPAccount(@RequestBody ValidateRiskVIPReqDTO request) throws Exception {
        log.info("creating VIP customer with id: "+ request.getCustomerId());
        return ResponseEntity.ok().body(customerService.validateVIPAccount(request));
    }

    @PostMapping(value = "/record-trx")
    public ResponseEntity<RecordCustomerTrxResDTO> RecordCustomerTransactions(@RequestBody RecordCustomerTrxReqDTO request) {
        log.info("record customer trx with cid and amount: "+ request.getCustomerId() + "" + request.getTransactionAmount());
        return ResponseEntity.ok().body(transactionService.recordTrx(request));
    }


    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedCustomers> getAllCustomers(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(customerService.findAll(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomersDTO> getCustomerByID(@PathVariable String id) {
        return ResponseEntity.ok().body(customerService.findById(id));
    }

    @PutMapping(value = "/block/{customerId}/{block}")
    public ResponseEntity<String> BlockCustomer(@PathVariable String customerId, @PathVariable boolean block) {
        log.info("blocking customer with id: "+ customerId);
        return ResponseEntity.ok().body(customerService.blockbyCustomerId(customerId, block));
    }

    @GetMapping(value = "/aml/{id}")
    public ResponseEntity<CustomerAmlResDTO> getAMLData(@PathVariable String id) {
        return ResponseEntity.ok().body(customerService.getAmlData(id));
    }

    @GetMapping(value = "/transactions/{id}")
    public ResponseEntity<List<CustomerTrxResDTO>> getTransactions(@PathVariable String id) {
        return ResponseEntity.ok().body(customerService.getTransactions(id));
    }


    @PostMapping(value = "/upload/{customerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFile(@ModelAttribute() UploadIdDTO uploadIdDTO) {
        log.info("uploading file  with customer id: "+ uploadIdDTO.getCustomerId());
        return ResponseEntity.ok(customerService.uploadId(uploadIdDTO.getFront(), uploadIdDTO.getBack(), uploadIdDTO));
    }

    @GetMapping(value = "idCard/{customerId}")
    public ResponseEntity<IdCard> getIdCard(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getIdCard(customerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
