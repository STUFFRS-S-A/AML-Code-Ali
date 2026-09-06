package com.appopay.visa.service;

import com.appopay.visa.entity.Customers;
import com.appopay.visa.entity.IamEntity;
import com.appopay.visa.model.CustomerDTO;
import com.appopay.visa.model.IamDTO;
import com.appopay.visa.repository.CustomerRepository;
import com.appopay.visa.repository.IamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IamService {

    @Autowired
    private IamRepository iamRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private OFACService ofacService;

    public boolean existsByUsername(String username) {
        return iamRepository.findByUserName(username).isPresent();
    }

    public String login(IamDTO loginRequest) {
        Optional<IamEntity> userOptional = iamRepository.findByUserName(loginRequest.getUserName());
        if (userOptional.isPresent()) {
            // User is found, get the user object
            IamEntity foundUser = userOptional.orElseThrow();

            if (passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
                // Password matches, user is authenticated
                return "Login successful!";
            } else {
                // Password does not match, return invalid password message
                return "Invalid password!";
            }
        } else {
            // User not found, return appropriate message
            return "Username not found!";
        }
    }
    public String signUpRegister(IamDTO signupRequest) {
        if (existsByUsername(signupRequest.getUserName())) {
            return "Username is already taken!";
        }
        if(!ofacService.isApproved(signupRequest.getName())){
            return "OFAC NOT APPROVED";
        }

        IamEntity iamEntity = new IamEntity();
        iamEntity.setUserName(signupRequest.getUserName());
        iamEntity.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        iamRepository.save(iamEntity);
        return "SignUp Successful";
    }

    public ResponseEntity<CustomerDTO> getProfile(Long id) {
        Optional<Customers> customerProfile = customerRepository.findById(id);
        if (customerProfile.isPresent()) {
            Customers customer = customerProfile.get();
            return new ResponseEntity<>(convertToDTO(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    private CustomerDTO convertToDTO(Customers customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFistName(customer.getFistName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setGender(customer.getGender());
        customerDTO.setCreatedAt(customer.getCreatedAt());
        customerDTO.setUpdatedAt(customer.getUpdatedAt());
        return customerDTO;
    }
}
