package com.appopay.visa.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Table(name = "iam")
@Data
@Entity
public class IamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "iam_id")
    private List<DeviceEntity> devices;

    private Instant createdAt;

    private Instant updatedAt;
}
