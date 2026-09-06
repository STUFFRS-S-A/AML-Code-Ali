package com.appopay.visa.model;

import com.appopay.visa.entity.CustomerToken;
import com.appopay.visa.entity.Customers;
import lombok.Data;

import java.time.Instant;

@Data
public class CustomerTokenDTO {

    private String id;
    private String token;
    private Instant createdAt;
    private Instant updatedAt;

    public CustomerToken toEntity(){
        CustomerToken customerToken = new CustomerToken();
        customerToken.setId(this.id);
        customerToken.setToken(this.token);

        return customerToken;
    }
}
