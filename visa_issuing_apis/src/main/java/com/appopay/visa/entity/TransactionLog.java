package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "transactionlog")
@Data
@Entity
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rawLogs;

    private Instant createdAt;

    private Instant updatedAt;

}
