package com.appopay.visa.model;

import lombok.Data;
import java.time.Instant;

@Data
public class OFACDTO {

    private Long id;
    private String name;
    private Boolean isBLocked;
    private Instant createdAt;
    private Instant updatedAt;

}
