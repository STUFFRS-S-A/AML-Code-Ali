package com.appopay.aml.model;

import com.appopay.aml.entity.Address;
import com.appopay.aml.entity.Person;
import lombok.Data;

@Data
public class AddressDTO {

    Long id;
    String zipCode;
    String state;
    String Province;
    String Country;

    public Address toEntity() {
        Address address = new Address();
        address.setId(this.getId());
        address.setZipCode(this.zipCode);
        address.setState(this.state);
        address.setProvince(this.Province);
        address.setCountry(this.Country);
        return address;
    }
}