package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.entity.MerchantPEPFamily;
import com.appopay.aml.util.RiskStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MerchantDTO {

    Long id;
    String legalName;
    String tradeName;
    String risk;
    RiskStatus riskStatus;
    String typeOfBusiness;
    String startDate;
    String country;
    String telephone;
    String email;
    String ruc;
    String legalRepName;
    String license;
    String title;
    String dob;
    String nationality;
    String address;
    String contactName;
    String contactTelephone;
    String contactEmail;
    String monthlySales;
    List<String> transactionTypech;
    String averageTicket;
    String monthlyTransactions;
    String commercialReferences;
    String accountName;
    String accountType;
    String bankName;
    String accountNumber;
    String pepStatus;
    String pepPosition;
    String pepStartDate;
    String pepEndDate;
    Boolean linkedToPEP;
    List<MerchantPEPFamilyDTO> pepFamilyDetails;

    private Boolean isBlocked;

    public Merchant toEntity() {
        Merchant merchant = new Merchant();
        merchant.setLegalName(this.getLegalName());
        merchant.setRisk(this.getRisk());
        merchant.setTradeName(this.getTradeName());
        merchant.setTypeOfBusiness(this.getTypeOfBusiness());
        merchant.setStartDate(this.getStartDate());
        merchant.setCountry(this.getCountry());
        merchant.setTelephone(this.getTelephone());
        merchant.setEmail(this.getEmail());
        merchant.setRuc(this.getRuc());
        merchant.setLegalRepName(this.getLegalRepName());
        merchant.setLicense(this.getLicense());
        merchant.setTitle(this.getTitle());
        merchant.setDob(this.getDob());
        merchant.setNationality(this.getNationality());
        merchant.setAddress(this.getAddress());
        merchant.setContactName(this.getContactName());
        merchant.setContactTelephone(this.getContactTelephone());
        merchant.setContactEmail(this.getContactEmail());
        merchant.setMonthlySales(this.getMonthlySales());
        merchant.setTransactionTypech(String.join(",", this.getTransactionTypech()));
        merchant.setAverageTicket(this.getAverageTicket());
        merchant.setMonthlyTransactions(this.getMonthlyTransactions());
        merchant.setCommercialReferences(this.getCommercialReferences());
        merchant.setAccountName(this.getAccountName());
        merchant.setAccountType(this.getAccountType());
        merchant.setBankName(this.getBankName());
        merchant.setAccountNumber(this.getAccountNumber());
        merchant.setIsBlocked(this.getIsBlocked());
        merchant.setPepStatus(this.getPepStatus());
        merchant.setPepPosition(this.getPepPosition());
        merchant.setPepStartDate(this.getPepStartDate());
        merchant.setPepEndDate(this.getPepEndDate());
        merchant.setLinkedToPEP(this.linkedToPEP);
        List<MerchantPEPFamily> list = new ArrayList<>();
        if(this.pepFamilyDetails != null){
            for(MerchantPEPFamilyDTO merchantPEPFamilyDTO: this.pepFamilyDetails){
                list.add(merchantPEPFamilyDTO.toEntity());
            }
        }
        merchant.setPepFamilyDetails(list);
        return merchant;
    }
}