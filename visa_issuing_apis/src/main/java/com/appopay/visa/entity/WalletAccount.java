package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "wallet_account")
@Data
@Entity
public class WalletAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String number;

    private Instant createdAt;

    private Instant updatedAt;

}
