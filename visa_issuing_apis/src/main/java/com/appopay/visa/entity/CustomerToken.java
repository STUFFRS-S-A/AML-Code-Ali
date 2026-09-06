package com.appopay.visa.entity;


import com.appopay.visa.model.CustomerTokenDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "customer_token")
@Data
@Entity
public class CustomerToken {
    @Id
    private String id;
    private String token;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public CustomerTokenDTO toDTO(){
        CustomerTokenDTO customerTokenDTO = new CustomerTokenDTO();
        customerTokenDTO.setId(this.id);
        customerTokenDTO.setToken(this.token);
        customerTokenDTO.setCreatedAt(this.createdAt);
        customerTokenDTO.setUpdatedAt(this.updatedAt);

        return customerTokenDTO;
    }


}
