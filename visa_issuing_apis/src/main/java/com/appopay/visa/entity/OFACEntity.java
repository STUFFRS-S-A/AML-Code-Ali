package com.appopay.visa.entity;

import com.appopay.visa.model.OFACDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Table(name = "ofac")
@Data
@Entity
public class OFACEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean isBLocked;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;


    public OFACDTO toDTO(){
        OFACDTO ofacDto = new OFACDTO();
        ofacDto.setId(this.getId());
        ofacDto.setName(this.getName());
        ofacDto.setIsBLocked(this.getIsBLocked());
        ofacDto.setCreatedAt(this.getCreatedAt());
        ofacDto.setUpdatedAt(this.getUpdatedAt());

        return ofacDto;
    }
}
