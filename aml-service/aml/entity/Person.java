package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "person")
@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String DOB ;
    private String nationality;
    private String fullResAddress;
    private String PercentageOfOwnership;
    private Boolean PEP;
    private Boolean islinkedToPEPpPosition;
    private String countryOfResidence;
    private String cityOfResidence;
}
