package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "address")
@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zipCode;
    private String state;
    private String Province;
    private String Country;
}
