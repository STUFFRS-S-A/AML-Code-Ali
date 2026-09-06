package com.appopay.aml.controller;


import com.appopay.aml.entity.Transaction;
import com.appopay.aml.model.PaginatedTransactions;
import com.appopay.aml.model.transaction.TransactionDTO;
import com.appopay.aml.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transaction/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("{page}/{size}")
    public ResponseEntity<PaginatedTransactions> getAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        return ResponseEntity.ok().body(transactionService.getAll(PageRequest.of(page,size)));
    }

    @GetMapping("flagged/{page}/{size}")
    public ResponseEntity<PaginatedTransactions> getAllFlagged(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        return ResponseEntity.ok().body(transactionService.getAllFlagged(PageRequest.of(page,size)));
    }

    @PutMapping
    public ResponseEntity<Transaction> updateOne(@RequestBody TransactionDTO req) {
        return ResponseEntity.ok().body(transactionService.updateOne(req));
    }

}
