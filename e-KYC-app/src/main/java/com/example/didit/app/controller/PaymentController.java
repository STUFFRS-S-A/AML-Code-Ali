package com.example.didit.app.controller;

import com.example.didit.app.model.payment.CashoutRequestDTO;
import com.example.didit.app.model.payment.PaymentRequestDTO;
import com.example.didit.app.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/validate")
    public ResponseEntity<Object> validateCustomer(@RequestBody PaymentRequestDTO request) throws Exception {
        Object response = paymentService.validateCustomer(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Object> sendOtp(@RequestBody PaymentRequestDTO request) throws Exception {
        Object response = paymentService.sendOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<Object> resendOtp(@RequestBody PaymentRequestDTO request) throws Exception {
        Object response = paymentService.resendOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/customer-enquiry")
    public ResponseEntity<Object> customerEnquiry(@RequestBody PaymentRequestDTO request) throws Exception {
        Object response = paymentService.custEnquiry(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/decrypt-card-num")
    public ResponseEntity<Object> decryptCardNum(@RequestBody PaymentRequestDTO request) throws Exception {
        Object response = paymentService.decryptCardNum(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cashout")
    public ResponseEntity<Object> cashout(@RequestBody CashoutRequestDTO request) throws Exception {
        Object response = paymentService.cashout(request);
        return ResponseEntity.ok(response);
    }
}
