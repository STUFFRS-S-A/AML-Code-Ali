package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Person;
import com.appopay.aml.util.RiskStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AgentDTO {

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

    public Agent toEntity() {
        Agent agent = new Agent();
        agent.setId(this.id);
        agent.setCompRegName(this.compRegName);
        agent.setRisk(this.risk);
        agent.setCompTradeName(this.compTradeName);
        agent.setCompTaxNumber(this.compTaxNumber);
        agent.setCompanyRegNumber(this.companyRegNumber);
        agent.setCompRegCountry(this.compRegCountry);
        agent.setCompRegDate(this.compRegDate);
        agent.setCompRegProvince(this.compRegProvince);
        if (this.currAddress != null) agent.setCurrAddress(this.currAddress.toEntity());
        if (this.phyAddress != null) agent.setPhyAddress(this.phyAddress.toEntity());
        if (this.postAddress != null) agent.setPostAddress(this.postAddress.toEntity());
        agent.setMainPhoneNo(this.mainPhoneNo);
        agent.setSecPhoneNumber(this.secPhoneNumber);
        agent.setCompWebsite(this.compWebsite);
        agent.setTradeNameWebsite(this.tradeNameWebsite);
        agent.setIsListedOnSE(this.isListedOnSE);
        agent.setExchangeName(this.ExchangeName);
        agent.setSymbolListed(this.symbolListed);
        agent.setIsRegByFinEntity(this.isRegByFinEntity);
        agent.setIsBlocked(this.isBlocked);
        agent.setIsRegByFinSerRegulator(this.isRegByFinSerRegulator);
        agent.setFinEntity(this.FinEntity);
        agent.setFinSerRegulatorName(this.FinSerRegulatorName);
        agent.setPrimPerContactName(this.primPerContactName);
        agent.setPrimPerEmail(this.primPerEmail);
        agent.setPrimPerPhoneNo(this.primPerPhoneNo);
        agent.setPrimPerPosition(this.primPerPosition);
        agent.setPrimPerExtension(this.primPerExtension);
        agent.setAuthsignName(this.authsignName);
        agent.setAuthsignPosition(this.authsignPosition);
        agent.setFinancingBankName(this.financingBankName);
        agent.setFinancingBankSwiftCode(this.financingBankSwiftCode);
        agent.setFundingAccountName(this.fundingAccountName);
        agent.setFundingAccHolderRelation(this.fundingAccHolderRelation);
        agent.setCurrencies(this.currencies);
        List<Person> beneficialOweners = new ArrayList<>();
        if (this.getBeneficialOweners() != null) {
            for (PersonDTO personDTO : this.getBeneficialOweners()) {
                beneficialOweners.add(personDTO.toEntity());
            }
        }
        agent.setBeneficialOweners(beneficialOweners);
        List<Person> controlOweners = new ArrayList<>();
        if (this.getControlOweners() != null) {
            for (PersonDTO personDTO : this.getControlOweners()) {
                controlOweners.add(personDTO.toEntity());
            }
        }
        agent.setControlOweners(controlOweners);

        return agent;
    }
}