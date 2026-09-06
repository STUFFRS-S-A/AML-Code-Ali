package com.appopay.aml.entity;

import com.appopay.aml.util.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Table(name = "merchant")
@Data
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String legalName;
    private String risk;
    private RiskStatus riskStatus;
    private String tradeName;
    private String typeOfBusiness;
    private String startDate;
    private String country;
    private String telephone;
    private String email;
    private String ruc;
    private String legalRepName;
    private String license;
    private String title;
    private String dob;
    private String nationality;
    private String address;
    private String contactName;
    private String contactTelephone;
    private String contactEmail;
    private String monthlySales;
    private String transactionTypech;
    private String averageTicket;
    private String monthlyTransactions;
    private String commercialReferences;
    private String accountName;
    private String accountType;
    private String bankName;
    private String accountNumber;
    private String pepStatus;
    private String pepPosition;
    private String pepStartDate;
    private String pepEndDate;
    private Boolean linkedToPEP;
    private Boolean isBlocked;
    private String frontIdURL;
    private String backIdURL;
    private String compRegistrationURL;
    private String licenseURL;
    private String others1URL;
    private String others2URL;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "merchant_id")
    private List<MerchantPEPFamily> pepFamilyDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mpa_id")
    private MPADetails mpaDetails;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

}
