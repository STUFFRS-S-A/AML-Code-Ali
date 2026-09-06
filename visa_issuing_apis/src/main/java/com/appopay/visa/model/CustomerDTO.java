package com.appopay.visa.model;

import com.appopay.visa.entity.Customers;
import lombok.Data;

import java.time.Instant;

@Data
public class CustomerDTO {

    private String id;
    private String fistName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String gender;
    private Instant createdAt;
    private Instant updatedAt;

    public Customers toEntity(){
        Customers customers = new Customers();
        customers.setId(this.id);
        customers.setFistName(this.fistName);
        customers.setLastName(this.lastName);
        customers.setPhoneNumber(this.phoneNumber);
        customers.setEmail(this.email);
        customers.setAddress(this.address);
        customers.setGender(this.gender);

        return customers;
    }
}
