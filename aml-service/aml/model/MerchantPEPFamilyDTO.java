package com.appopay.aml.model;

import com.appopay.aml.entity.Merchant;
import com.appopay.aml.entity.MerchantPEPFamily;
import lombok.Data;

@Data
public class MerchantPEPFamilyDTO {

    Long id;
    String fullname;
    String kinship;
    String positonheld;
    String dateofstartinofffice;
    String dateoftermination;

    public MerchantPEPFamily toEntity() {
        MerchantPEPFamily merchantPEPFamily = new MerchantPEPFamily();
        merchantPEPFamily.setFullname(this.fullname);
        merchantPEPFamily.setKinship(this.kinship);
        merchantPEPFamily.setPositonheld(this.positonheld);
        merchantPEPFamily.setDateofstartinofffice(this.dateofstartinofffice);
        merchantPEPFamily.setDateoftermination(this.dateoftermination);
        return merchantPEPFamily;
    }
}