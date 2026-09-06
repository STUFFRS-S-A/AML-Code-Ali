package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "card")
@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    private String cardHolderName;

    private String expDate;

    private String issueDate;

    private String cvv;

    @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customers customers;

    private Instant createdAt;

    private Instant updatedAt;

}
