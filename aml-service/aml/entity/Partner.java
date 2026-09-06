package com.appopay.aml.entity;

import com.appopay.aml.util.RiskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "partner")
@Data
@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String compRegName;
    private String risk;
    private RiskStatus riskStatus;
    private String compTradeName;
    private String compTaxNumber;
    private String companyRegNumber;
    private String compRegCountry;
    private String compRegDate;
    private String compRegProvince;

    private Boolean isBlocked;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "curr_add_id")
    private Address currAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phy_add_id")
    private Address phyAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_add_id")
    private Address postAddress;
    private String mainPhoneNo;
    private String secPhoneNumber;
    private String compWebsite;
    private String tradeNameWebsite;
    private Boolean isListedOnSE;
    private String exchangeName;
    private String symbolListed;
    private Boolean isRegByFinEntity;
    private Boolean isRegByFinSerRegulator;
    private String finEntity;
    private String finSerRegulatorName;
    private String primPerContactName;
    private String primPerEmail;
    private String primPerPhoneNo;
    private String primPerPosition;
    private String primPerExtension;
    private String authsignName;
    private String authsignPosition;
    private String financingBankName;
    private String financingBankSwiftCode;
    private String fundingAccountName;
    private String fundingAccHolderRelation;
    private String currencies;

    private String frontIdURL;
    private String backIdURL;
    private String compRegistrationURL;
    private String licenseURL;
    private String others1URL;
    private String others2URL;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mpa_id")
    private MPADetails mpaDetails;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_beneficial_id")
    private List<Person> beneficialOweners;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_control_id")
    private List<Person> controlOweners;
}
