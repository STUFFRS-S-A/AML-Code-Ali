package com.appopay.visa.service;

import com.appopay.visa.entity.CustomerToken;
import com.appopay.visa.entity.Customers;
import com.appopay.visa.entity.IamEntity;
import com.appopay.visa.Exception.CustomException;
import com.appopay.visa.model.CustomerDTO;
import com.appopay.visa.model.CustomerTokenDTO;
import com.appopay.visa.model.IamDTO;
import com.appopay.visa.repository.CustomerRepository;
import com.appopay.visa.repository.CustomerTokenRepository;
import com.appopay.visa.repository.IamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerTokenRepository customerTokenRepository;

    public CustomerDTO create(CustomerDTO customerDTO) {
        Optional<Customers> optionalCustomers = customerRepository.findById(customerDTO.getId());
        if (optionalCustomers.isPresent()) {
            throw new CustomException("Customer exists with this id");
        } else {
            return customerRepository.save(customerDTO.toEntity()).toDTO();
        }
    }

    public CustomerDTO updateOne(CustomerDTO customerDTO) {
        if (customerDTO.getId() == null) {
            throw new CustomException("please enter a customer id");
        }
        Optional<Customers> optionalCustomers = customerRepository.findById(customerDTO.getId());

        if (optionalCustomers.isEmpty()) {
            throw new CustomException("customer not found");
        }
        Customers customers = optionalCustomers.get();

        if (customerDTO.getId() != null)
            customers.setId(customerDTO.getId());
        if (customerDTO.getFistName() != null)
            customers.setFistName(customerDTO.getFistName());
        if (customerDTO.getLastName() != null)
            customers.setLastName(customerDTO.getLastName());
        if (customerDTO.getPhoneNumber() != null)
            customers.setPhoneNumber(customerDTO.getPhoneNumber());
        if (customerDTO.getEmail() != null)
            customers.setEmail(customerDTO.getEmail());
        if (customerDTO.getAddress() != null)
            customers.setAddress(customerDTO.getAddress());
        if (customerDTO.getGender() != null)
            customers.setGender(customerDTO.getGender());

        return customerRepository.save(customers).toDTO();
    }

    public CustomerDTO getById(String id) {
        Optional<Customers> customers = customerRepository.findById(id);
        if (customers.isPresent()) {
            return customers.get().toDTO();
        }
        throw new CustomException("Customer not found");
    }

    public CustomerTokenDTO upsert(CustomerTokenDTO req) {
        Optional<CustomerToken> optionalCustomerToken = customerTokenRepository.findById(req.getId());
        if (optionalCustomerToken.isPresent()) {
            CustomerToken customerToken = optionalCustomerToken.get();
            customerToken.setToken(req.getToken());
            return customerTokenRepository.save(customerToken).toDTO();
        } else {
            return customerTokenRepository.save(req.toEntity()).toDTO();
        }
    }

    public CustomerTokenDTO getToken(String id) {
        Optional<CustomerToken> optionalCustomerToken = customerTokenRepository.findById(id);
        if (optionalCustomerToken.isEmpty()) {
            throw new CustomException("customer not found");
        } else {
            return optionalCustomerToken.get().toDTO();
        }
    }
}
