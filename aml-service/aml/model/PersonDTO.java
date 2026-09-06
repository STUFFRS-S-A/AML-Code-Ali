package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Person;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PersonDTO {

    Long id;
    String fullName;
    String DOB;
    String nationality;
    String fullResAddress;
    String PercentageOfOwnership;
    Boolean PEP;
    Boolean islinkedToPEPpPosition;
    String countryOfResidence;
    String cityOfResidence;
    public Person toEntity() {
        Person person = new Person();
        person.setId(this.getId());
        person.setFullName(this.getFullName());
        person.setDOB(this.getDOB());
        person.setNationality(this.getNationality());
        person.setFullResAddress(this.getFullResAddress());
        person.setPercentageOfOwnership(this.getPercentageOfOwnership());
        person.setPEP(this.getPEP());
        person.setIslinkedToPEPpPosition(this.getIslinkedToPEPpPosition());
        person.setCountryOfResidence(this.getCountryOfResidence());
        person.setCityOfResidence(this.getCityOfResidence());
        return person;
    }
}