package com.appopay.aml.repository;

import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRiskConfigRepository extends JpaRepository<CountryRiskConfig, Long> {

    CountryRiskConfig findByCountryIgnoreCase(String country);

}
