package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.*;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.MerchantRepository;
import com.appopay.aml.util.RiskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.appopay.aml.service.CustomerService.getFileHashName;

@Service
public class MerchantService {

    private static final Logger log = LoggerFactory.getLogger(MerchantService.class);
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;
    @Autowired
    private S3Service s3Service;
    @Value("${s3Url}")
    private String baseUrl;

    public Merchant createMerchant(MerchantDTO merchantDTO) {
        if (merchantDTO.getId() != null) {
            throw new CustomException("new agent can not have ID");
        } else {
            Merchant merchant = merchantDTO.toEntity();
            merchant.setRisk(getRisk(merchantDTO.getCountry()));
            return merchantRepository.save(merchant);
        }
    }

    public Merchant updateOne(MerchantDTO merchantDTO) {
        if (merchantDTO.getId() == null) {
            throw new CustomException("please enter an merchant Id");
        }
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantDTO.getId());

        if (optionalMerchant.isEmpty()) {
            throw new CustomException("merchant not found");
        }

        Merchant merchant = optionalMerchant.get();

        if (merchantDTO.getLegalName() != null) merchant.setLegalName(merchantDTO.getLegalName());
        if (merchantDTO.getRiskStatus() != null) {
            merchant.setRisk(merchantDTO.getRiskStatus().getValue());
            merchant.setRiskStatus(RiskStatus.valueOf(merchantDTO.getRiskStatus().name()));
        }
        if (merchantDTO.getTradeName() != null) merchant.setTradeName(merchantDTO.getTradeName());
        if (merchantDTO.getTypeOfBusiness() != null) merchant.setTypeOfBusiness(merchantDTO.getTypeOfBusiness());
        if (merchantDTO.getStartDate() != null) merchant.setStartDate(merchantDTO.getStartDate());
        if (merchantDTO.getCountry() != null) merchant.setCountry(merchantDTO.getCountry());
        if (merchantDTO.getTelephone() != null) merchant.setTelephone(merchantDTO.getTelephone());
        if (merchantDTO.getEmail() != null) merchant.setEmail(merchantDTO.getEmail());
        if (merchantDTO.getRuc() != null) merchant.setRuc(merchantDTO.getRuc());
        if (merchantDTO.getLegalRepName() != null) merchant.setLegalRepName(merchantDTO.getLegalRepName());
        if (merchantDTO.getLicense() != null) merchant.setLicense(merchantDTO.getLicense());
        if (merchantDTO.getTitle() != null) merchant.setTitle(merchantDTO.getTitle());
        if (merchantDTO.getDob() != null) merchant.setDob(merchantDTO.getDob());
        if (merchantDTO.getNationality() != null) merchant.setNationality(merchantDTO.getNationality());
        if (merchantDTO.getAddress() != null) merchant.setAddress(merchantDTO.getAddress());
        if (merchantDTO.getContactName() != null) merchant.setContactName(merchantDTO.getContactName());
        if (merchantDTO.getContactTelephone() != null) merchant.setContactTelephone(merchantDTO.getContactTelephone());
        if (merchantDTO.getContactEmail() != null) merchant.setContactEmail(merchantDTO.getContactEmail());
        if (merchantDTO.getMonthlySales() != null) merchant.setMonthlySales(merchantDTO.getMonthlySales());
        if (merchantDTO.getIsBlocked() != null) merchant.setIsBlocked(merchantDTO.getIsBlocked());
        if (merchantDTO.getTransactionTypech() != null)
            merchant.setTransactionTypech(String.join(",", merchantDTO.getTransactionTypech()));
        if (merchantDTO.getAverageTicket() != null) merchant.setAverageTicket(merchantDTO.getAverageTicket());
        if (merchantDTO.getMonthlyTransactions() != null)
            merchant.setMonthlyTransactions(merchantDTO.getMonthlyTransactions());
        if (merchantDTO.getCommercialReferences() != null)
            merchant.setCommercialReferences(merchantDTO.getCommercialReferences());
        if (merchantDTO.getAccountName() != null) merchant.setAccountName(merchantDTO.getAccountName());
        if (merchantDTO.getAccountType() != null) merchant.setAccountType(merchantDTO.getAccountType());
        if (merchantDTO.getBankName() != null) merchant.setBankName(merchantDTO.getBankName());
        if (merchantDTO.getAccountNumber() != null) merchant.setAccountNumber(merchantDTO.getAccountNumber());
        if (merchantDTO.getPepStatus() != null) merchant.setPepStatus(merchantDTO.getPepStatus());
        if (merchantDTO.getPepPosition() != null) merchant.setPepPosition(merchantDTO.getPepPosition());
        if (merchantDTO.getPepStartDate() != null) merchant.setPepStartDate(merchantDTO.getPepStartDate());
        if (merchantDTO.getPepEndDate() != null) merchant.setPepEndDate(merchantDTO.getPepEndDate());
        if (merchantDTO.getLinkedToPEP() != null) {
            merchant.setLinkedToPEP(merchantDTO.getLinkedToPEP());
        }
        if (merchantDTO.getPepFamilyDetails() != null)
            merchant.setPepFamilyDetails(merchantDTO.toEntity().getPepFamilyDetails());
        return merchantRepository.save(merchant);
    }

    public Merchant getById(Long id) {
        Optional<Merchant> merchant = merchantRepository.findById(id);
        if (merchant.isPresent()) {
            return merchant.get();
        }
        throw new CustomException("Merchant not found");
    }

    public PaginatedMerchant findAll(Pageable pageable) {
        PaginatedMerchant response = new PaginatedMerchant();
        response.setData(merchantRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(merchantRepository.count());
        return response;
    }

    public String getRisk(String country) {
        CountryRiskConfig countryRiskConfig = countryRiskConfigRepository.findByCountryIgnoreCase(country);
        Long risk = null;
        if (countryRiskConfig != null) risk = Long.valueOf(countryRiskConfig.getRiskScoreNationality());
        if (risk == null) return "null";
        else {
            return risk.toString();
        }
    }

    public Boolean deleteMerchant(long id) {
        if (merchantRepository.existsById(id)) {
            merchantRepository.deleteById(id);
            return true;
        }
        throw new CustomException("ID not found");
    }

    public List<String> uploadDocuments(UploadDocumentDTO request) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(request.getId());
        if (optionalMerchant.isEmpty()) throw new CustomException("merchant not found");
        if (request.getFrontId() == null || request.getBackId() == null || request.getCompRegistration() == null || request.getLicense() == null)
            throw new CustomException("required document is null");

        Merchant merchant = optionalMerchant.get();
        List<String> response = new ArrayList<>();

        List<MultipartFile> files = new ArrayList<>(List.of(request.getFrontId(), request.getBackId(), request.getCompRegistration(), request.getLicense()));
        List<String> urlNames = new ArrayList<>(Arrays.asList("frontIdUrl", "backIdUrl", "compRegistrationUrl", "licenseUrl"));
        if (request.getOthers1() != null) {
            files.add(request.getOthers1());
            urlNames.add("others1Url");
        }
        if (request.getOthers2() != null) {
            files.add(request.getOthers2());
            urlNames.add("others2Url");
        }

        for (int i = 0; i < files.size(); i++) {
            String keyName = getFileHashName(files.get(i));
            s3Service.uploadFile(files.get(i), "mer" + keyName);
            String url = baseUrl + "mer" + keyName;

            switch (urlNames.get(i)) {
                case "frontIdUrl":
                    merchant.setFrontIdURL(url);
                    break;
                case "backIdUrl":
                    merchant.setBackIdURL(url);
                    break;
                case "compRegistrationUrl":
                    merchant.setCompRegistrationURL(url);
                    break;
                case "licenseUrl":
                    merchant.setLicenseURL(url);
                case "others1Url":
                    merchant.setOthers1URL(url);
                case "others2Url":
                    merchant.setOthers2URL(url);
                    break;

            }
            response.add(urlNames.get(i) + ": " + url);
        }
        merchantRepository.save(merchant);

        return response;
    }

    public List<String> updateDocuments(UploadDocumentDTO request) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(request.getId());
        if (optionalMerchant.isEmpty()) throw new CustomException("merchant not found");


        Merchant merchant = optionalMerchant.get();
        List<String> response = new ArrayList<>();

        List<MultipartFile> files = new ArrayList<>();
        List<String> urlNames = new ArrayList<>();
        if (request.getFrontId() != null) {
            files.add(request.getFrontId());
            urlNames.add("frontIdUrl");
        }
        if (request.getBackId() != null) {
            files.add(request.getBackId());
            urlNames.add("backIdUrl");
        }
        if (request.getCompRegistration() != null) {
            files.add(request.getCompRegistration());
            urlNames.add("compRegistrationUrl");
        }
        if (request.getLicense() != null) {
            files.add(request.getLicense());
            urlNames.add("licenseUrl");
        }
        if (request.getOthers1() != null) {
            files.add(request.getOthers1());
            urlNames.add("others1Url");
        }
        if (request.getOthers2() != null) {
            files.add(request.getOthers2());
            urlNames.add("others2Url");
        }

        for (int i = 0; i < files.size(); i++) {
            String keyName = getFileHashName(files.get(i));
            s3Service.uploadFile(files.get(i), "mer" + keyName);
            String url = baseUrl + "mer" + keyName;

            switch (urlNames.get(i)) {
                case "frontIdUrl":
                    merchant.setFrontIdURL(url);
                    break;
                case "backIdUrl":
                    merchant.setBackIdURL(url);
                    break;
                case "compRegistrationUrl":
                    merchant.setCompRegistrationURL(url);
                    break;
                case "licenseUrl":
                    merchant.setLicenseURL(url);
                case "others1Url":
                    merchant.setOthers1URL(url);
                case "others2Url":
                    merchant.setOthers2URL(url);
                    break;

            }
            response.add(urlNames.get(i) + ": " + url);
        }
        merchantRepository.save(merchant);

        return response;

    }

    public void deleteFile(String keyName) {
        s3Service.deleteFile(keyName);
    }

    public void clearField(DeleteOption deleteOption, long id) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if (optionalMerchant.isEmpty()) {
            throw new CustomException("merchant not found");
        }
        try {
            Merchant merchant = optionalMerchant.get();
            Field field = Merchant.class.getDeclaredField(deleteOption.name());
            field.setAccessible(true);
            field.set(merchant, null);

            // Save the updated entity
            merchantRepository.save(merchant);
        } catch (Exception e) {
            throw new CustomException(e.toString());
        }
    }

    public Merchant updateToVIP(MPADetailsDTO mpaDetailsDTO) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(mpaDetailsDTO.getMpaId());
        if (optionalMerchant.isEmpty()) {
            throw new CustomException("merchant not found");
        }
        Merchant merchant = optionalMerchant.get();

        merchant.setMpaDetails(mpaDetailsDTO.toEntity());
        merchantRepository.save(merchant);
        return merchant;
    }


}
