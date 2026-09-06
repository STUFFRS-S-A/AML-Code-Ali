package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.PartnerRepository;
import com.appopay.aml.util.RiskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.appopay.aml.service.CustomerService.getFileHashName;

@Service
public class PartnerService {

    private static final Logger log = LoggerFactory.getLogger(PartnerService.class);
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private S3Service s3Service;

    @Value("${s3Url}")
    private String baseUrl;

    public Partner createPartner(PartnerDTO partnerDTO) {
        if (partnerDTO.getId() != null) {
            throw new CustomException("new partner can not have ID");
        } else {
            Partner partner = partnerDTO.toEntity();
            partner.setRisk(merchantService.getRisk(partnerDTO.getCompRegCountry()));
            return partnerRepository.save(partner);
        }
    }

    public Partner updateOne(PartnerDTO partnerDTO) {
        if (partnerDTO.getId() == null) {
            throw new CustomException("please enter a partner Id");
        }
        Optional<Partner> optionalPartner = partnerRepository.findById(partnerDTO.getId());

        if (optionalPartner.isEmpty()) {
            throw new CustomException("partner not found");
        }

        Partner partner = optionalPartner.get();

        if (partnerDTO.getCompRegName() != null) partner.setCompRegName(partnerDTO.getCompRegName());
        if (partnerDTO.getRiskStatus() != null) {
            partner.setRisk(partnerDTO.getRiskStatus().getValue());
            partner.setRiskStatus(RiskStatus.valueOf(partnerDTO.getRiskStatus().name()));
        }
        if (partnerDTO.getCompTradeName() != null) partner.setCompTradeName(partnerDTO.getCompTradeName());
        if (partnerDTO.getCompTaxNumber() != null) partner.setCompTaxNumber(partnerDTO.getCompTaxNumber());
        if (partnerDTO.getCompanyRegNumber() != null) partner.setCompanyRegNumber(partnerDTO.getCompanyRegNumber());
        if (partnerDTO.getCompRegCountry() != null) partner.setCompRegCountry(partnerDTO.getCompRegCountry());
        if (partnerDTO.getCompRegDate() != null) partner.setCompRegDate(partnerDTO.getCompRegDate());
        if (partnerDTO.getCompRegProvince() != null) partner.setCompRegProvince(partnerDTO.getCompRegProvince());
        if (partnerDTO.getCurrAddress() != null) partner.setCurrAddress(partnerDTO.toEntity().getCurrAddress());
        if (partnerDTO.getPhyAddress() != null) partner.setPhyAddress(partnerDTO.toEntity().getPhyAddress());
        if (partnerDTO.getPostAddress() != null) partner.setPostAddress(partnerDTO.toEntity().getPostAddress());
        if (partnerDTO.getMainPhoneNo() != null) partner.setMainPhoneNo(partnerDTO.getMainPhoneNo());
        if (partnerDTO.getSecPhoneNumber() != null) partner.setSecPhoneNumber(partnerDTO.getSecPhoneNumber());
        if (partnerDTO.getCompWebsite() != null) partner.setCompWebsite(partnerDTO.getCompWebsite());
        if (partnerDTO.getTradeNameWebsite() != null) partner.setTradeNameWebsite(partnerDTO.getTradeNameWebsite());
        if (partnerDTO.getIsListedOnSE() != null) partner.setIsListedOnSE(partnerDTO.getIsListedOnSE());
        if (partnerDTO.getExchangeName() != null) partner.setExchangeName(partnerDTO.getExchangeName());
        if (partnerDTO.getSymbolListed() != null) partner.setSymbolListed(partnerDTO.getSymbolListed());
        if (partnerDTO.getIsRegByFinEntity() != null) partner.setIsRegByFinEntity(partnerDTO.getIsRegByFinEntity());
        if (partnerDTO.getIsRegByFinSerRegulator() != null)
            partner.setIsRegByFinSerRegulator(partnerDTO.getIsRegByFinSerRegulator());
        if (partnerDTO.getFinEntity() != null) partner.setFinEntity(partnerDTO.getFinEntity());
        if (partnerDTO.getFinSerRegulatorName() != null)
            partner.setFinSerRegulatorName(partnerDTO.getFinSerRegulatorName());
        if (partnerDTO.getPrimPerContactName() != null)
            partner.setPrimPerContactName(partnerDTO.getPrimPerContactName());
        if (partnerDTO.getPrimPerEmail() != null) partner.setPrimPerEmail(partnerDTO.getPrimPerEmail());
        if (partnerDTO.getPrimPerPhoneNo() != null) partner.setPrimPerPhoneNo(partnerDTO.getPrimPerPhoneNo());
        if (partnerDTO.getPrimPerPosition() != null) partner.setPrimPerPosition(partnerDTO.getPrimPerPosition());
        if (partnerDTO.getPrimPerExtension() != null) partner.setPrimPerExtension(partnerDTO.getPrimPerExtension());
        if (partnerDTO.getAuthsignName() != null) partner.setAuthsignName(partnerDTO.getAuthsignName());
        if (partnerDTO.getAuthsignPosition() != null) partner.setAuthsignPosition(partnerDTO.getAuthsignPosition());
        if (partnerDTO.getFinancingBankName() != null) partner.setFinancingBankName(partnerDTO.getFinancingBankName());
        if (partnerDTO.getIsBlocked() != null) partner.setIsBlocked(partnerDTO.getIsBlocked());
        if (partnerDTO.getFinancingBankSwiftCode() != null)
            partner.setFinancingBankSwiftCode(partnerDTO.getFinancingBankSwiftCode());
        if (partnerDTO.getFundingAccountName() != null)
            partner.setFundingAccountName(partnerDTO.getFundingAccountName());
        if (partnerDTO.getFundingAccHolderRelation() != null)
            partner.setFundingAccHolderRelation(partnerDTO.getFundingAccHolderRelation());
        if (partnerDTO.getCurrencies() != null) partner.setCurrencies(partnerDTO.getCurrencies());
        if (partnerDTO.getBeneficialOweners() != null)
            partner.setBeneficialOweners(partnerDTO.toEntity().getBeneficialOweners());
        if (partnerDTO.getControlOweners() != null)
            partner.setControlOweners(partnerDTO.toEntity().getControlOweners());
        return partnerRepository.save(partner);
    }

    public Partner getById(Long id) {
        Optional<Partner> partner = partnerRepository.findById(id);
        if (partner.isPresent()) {
            return partner.get();
        }
        throw new CustomException("Partner not found");
    }

    public PaginatedPartner findAll(Pageable pageable) {
        PaginatedPartner response = new PaginatedPartner();
        response.setData(partnerRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(partnerRepository.count());
        return response;
    }

    @Transactional
    public Boolean deletePartner(long id) {
        if (partnerRepository.existsById(id)) {
            partnerRepository.deleteById(id);
            return true;
        }
        throw new CustomException("ID not found");
    }

    public List<String> uploadDocuments(UploadDocumentDTO request) {
        Optional<Partner> optionalPartner = partnerRepository.findById(request.getId());
        if (optionalPartner.isEmpty()) throw new CustomException("partner not found");

        Partner partner = optionalPartner.get();
        List<String> response = new ArrayList<>();

        List<MultipartFile> files = new ArrayList<>(List.of(request.getFrontId(), request.getBackId(), request.getCompRegistration(), request.getLicense()));
        List<String> urlNames = new ArrayList<>(Arrays.asList("frontIdUrl", "backIdUrl", "compRegistrationURL", "licenseURL"));
        if (request.getOthers1() != null) {
            files.add(request.getOthers1());
            urlNames.add("others1URL");
        }
        if (request.getOthers2() != null) {
            files.add(request.getOthers2());
            urlNames.add("others2URL");
        }

        for (int i = 0; i < files.size(); i++) {
            String keyName = getFileHashName(files.get(i));
            s3Service.uploadFile(files.get(i), "par" + keyName);
            String url = baseUrl + "par" + keyName;

            switch (urlNames.get(i)) {
                case "frontIdUrl":
                    partner.setFrontIdURL(url);
                    break;
                case "backIdUrl":
                    partner.setBackIdURL(url);
                    break;
                case "compRegistrationUrl":
                    partner.setCompRegistrationURL(url);
                    break;
                case "licenseUrl":
                    partner.setLicenseURL(url);
                case "others1URL":
                    partner.setOthers1URL(url);
                case "others2URL":
                    partner.setOthers2URL(url);
                    break;

            }
            response.add(urlNames.get(i) + ": " + url);
        }
        partnerRepository.save(partner);

        return response;
    }

    public List<String> updateDocuments(UploadDocumentDTO request) {
        Optional<Partner> optionalPartner = partnerRepository.findById(request.getId());
        if (optionalPartner.isEmpty()) throw new CustomException("partner not found");


        Partner partner = optionalPartner.get();
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
            s3Service.uploadFile(files.get(i), "par" + keyName);
            String url = baseUrl + "par" + keyName;

            switch (urlNames.get(i)) {
                case "frontIdUrl":
                    partner.setFrontIdURL(url);
                    break;
                case "backIdUrl":
                    partner.setBackIdURL(url);
                    break;
                case "compRegistrationUrl":
                    partner.setCompRegistrationURL(url);
                    break;
                case "licenseUrl":
                    partner.setLicenseURL(url);
                case "others1Url":
                    partner.setOthers1URL(url);
                case "others2Url":
                    partner.setOthers2URL(url);
                    break;

            }
            response.add(urlNames.get(i) + ": " + url);
        }
        partnerRepository.save(partner);

        return response;

    }

    public void clearField(DeleteOption deleteOption, long id) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        if (optionalPartner.isEmpty()) {
            throw new CustomException("partner not found");
        }
        try {
            Partner partner = optionalPartner.get();
            Field field = Partner.class.getDeclaredField(deleteOption.name());
            field.setAccessible(true);
            field.set(partner, null);

            // Save the updated entity
            partnerRepository.save(partner);
        } catch (Exception e) {
            throw new CustomException(e.toString());
        }
    }

    public Partner updateToVIP(MPADetailsDTO mpaDetailsDTO) {
        Optional<Partner> optionalPartner = partnerRepository.findById(mpaDetailsDTO.getMpaId());
        if (optionalPartner.isEmpty()) {
            throw new CustomException("partner not found");
        }
        Partner partner = optionalPartner.get();
        partner.setMpaDetails(mpaDetailsDTO.toEntity());
        partnerRepository.save(partner);
        return partner;
    }

}
