package com.appopay.aml.model;

import com.appopay.aml.entity.Partner;
import com.appopay.aml.entity.Person;
import com.appopay.aml.util.RiskStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PartnerDTO {

    Long id;
    String compRegName;
    String risk;
    RiskStatus riskStatus;
    String compTradeName;
    String compTaxNumber;
    String companyRegNumber;
    String compRegCountry;
    String compRegDate;
    String compRegProvince;
    AddressDTO currAddress;
    AddressDTO phyAddress;
    AddressDTO postAddress;
    String mainPhoneNo;
    String secPhoneNumber;
    String compWebsite;
    String tradeNameWebsite;
    Boolean isListedOnSE;
    String ExchangeName;
    String symbolListed;
    Boolean isRegByFinEntity;
    Boolean isRegByFinSerRegulator;
    String FinEntity;
    String FinSerRegulatorName;
    String primPerContactName;
    String primPerEmail;
    String primPerPhoneNo;
    String primPerPosition;
    String primPerExtension;
    String authsignName;
    String authsignPosition;
    String financingBankName;
    String financingBankSwiftCode;
    String fundingAccountName;
    String fundingAccHolderRelation;
    String currencies;
    List<PersonDTO> beneficialOweners;
    List<PersonDTO> controlOweners;

    private Boolean isBlocked;

    public Partner toEntity() {
        Partner partner = new Partner();
        partner.setId(this.id);
        partner.setCompRegName(this.compRegName);
        partner.setRisk(this.risk);
        partner.setCompTradeName(this.compTradeName);
        partner.setCompTaxNumber(this.compTaxNumber);
        partner.setCompanyRegNumber(this.companyRegNumber);
        partner.setCompRegCountry(this.compRegCountry);
        partner.setCompRegDate(this.compRegDate);
        partner.setCompRegProvince(this.compRegProvince);
        if (this.currAddress != null) partner.setCurrAddress(this.currAddress.toEntity());
        if (this.phyAddress != null) partner.setPhyAddress(this.phyAddress.toEntity());
        if (this.postAddress != null) partner.setPostAddress(this.postAddress.toEntity());
        partner.setMainPhoneNo(this.mainPhoneNo);
        partner.setSecPhoneNumber(this.secPhoneNumber);
        partner.setIsBlocked(this.isBlocked);
        partner.setCompWebsite(this.compWebsite);
        partner.setTradeNameWebsite(this.tradeNameWebsite);
        partner.setIsListedOnSE(this.isListedOnSE);
        partner.setExchangeName(this.ExchangeName);
        partner.setSymbolListed(this.symbolListed);
        partner.setIsRegByFinEntity(this.isRegByFinEntity);
        partner.setIsRegByFinSerRegulator(this.isRegByFinSerRegulator);
        partner.setFinEntity(this.FinEntity);
        partner.setFinSerRegulatorName(this.FinSerRegulatorName);
        partner.setPrimPerContactName(this.primPerContactName);
        partner.setPrimPerEmail(this.primPerEmail);
        partner.setPrimPerPhoneNo(this.primPerPhoneNo);
        partner.setPrimPerPosition(this.primPerPosition);
        partner.setPrimPerExtension(this.primPerExtension);
        partner.setAuthsignName(this.authsignName);
        partner.setAuthsignPosition(this.authsignPosition);
        partner.setFinancingBankName(this.financingBankName);
        partner.setFinancingBankSwiftCode(this.financingBankSwiftCode);
        partner.setFundingAccountName(this.fundingAccountName);
        partner.setFundingAccHolderRelation(this.fundingAccHolderRelation);
        partner.setCurrencies(this.currencies);
        List<Person> beneficialOweners = new ArrayList<>();
        if (this.getBeneficialOweners() != null) {
            for (PersonDTO personDTO : this.getBeneficialOweners()) {
                beneficialOweners.add(personDTO.toEntity());
            }
        }

        partner.setBeneficialOweners(beneficialOweners);

        List<Person> controlOweners = new ArrayList<>();
        if (this.getControlOweners() != null) {
            for (PersonDTO personDTO : this.getControlOweners()) {
                controlOweners.add(personDTO.toEntity());
            }
        }
        partner.setControlOweners(controlOweners);

        return partner;
    }
}