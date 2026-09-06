package com.appopay.aml.entity;

import com.appopay.aml.model.MerchantPEPFamilyDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "merchant_pep_family")
@Data
@Entity
public class MerchantPEPFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String kinship;
    private String positonheld;
    private String dateofstartinofffice;
    private String dateoftermination;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public MerchantPEPFamilyDTO toDTO() {
        MerchantPEPFamilyDTO merchantPEPFamilyDTO = new MerchantPEPFamilyDTO();
        merchantPEPFamilyDTO.setFullname(this.fullname);
        merchantPEPFamilyDTO.setKinship(this.kinship);
        merchantPEPFamilyDTO.setPositonheld(this.positonheld);
        merchantPEPFamilyDTO.setDateofstartinofffice(this.dateofstartinofffice);
        merchantPEPFamilyDTO.setDateoftermination(this.dateoftermination);
        return merchantPEPFamilyDTO;
    }

}
