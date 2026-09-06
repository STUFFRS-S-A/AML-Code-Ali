package com.appopay.visa.entity;


import com.appopay.visa.model.CustomerDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "customers")
@Data
@Entity
public class Customers {
    @Id
    private String id;
    private String fistName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String gender;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public CustomerDTO toDTO(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(this.id);
        customerDTO.setFistName(this.fistName);
        customerDTO.setLastName(this.lastName);
        customerDTO.setPhoneNumber(this.phoneNumber);
        customerDTO.setEmail(this.email);
        customerDTO.setAddress(this.address);
        customerDTO.setGender(this.gender);
        customerDTO.setCreatedAt(this.createdAt);
        customerDTO.setUpdatedAt(this.updatedAt);

        return customerDTO;
    }


}
