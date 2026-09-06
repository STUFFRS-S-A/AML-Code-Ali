package com.appopay.aml.controller;


import com.appopay.aml.entity.IAM;
import com.appopay.aml.model.*;
import com.appopay.aml.service.IAMService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class IAMController {

    private static final Logger log = LoggerFactory.getLogger(IAMController.class);
    @Autowired
    private IAMService iamService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Void> SignUpUser(@Validated(SignupReqDTO.PasswordRequired.class) @RequestBody SignupReqDTO request) {
        log.info("Sign up request and designation: " + request.getUsername() + ", " + request.getDesignation());
        iamService.SignUp(request);
        log.info("returning ok for signup req: " + request.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Void> loginUser(@RequestBody LoginReqDTO request) {
        log.info("login request: " + request.getUserName());
        iamService.login(request);
        log.info("returning ok for login req: " + request.getUserName());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@Validated(SignupReqDTO.PasswordOptional.class) @RequestBody SignupReqDTO request) {
        log.info("updating user: " + request.getUsername());
        iamService.updateUser(request);
        log.info("returning ok for update user " + request.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll/{page}/{size}")
    public ResponseEntity<PaginatedUsers> getAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok(iamService.getAllPaginsated(PageRequest.of(page, size)));
    }

    @GetMapping("/{username}")
    public ResponseEntity<IAM> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(iamService.getByUsername(username));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userName") String userName) {
        iamService.deleteUser(userName);
        return ResponseEntity.ok().build();
    }


}
