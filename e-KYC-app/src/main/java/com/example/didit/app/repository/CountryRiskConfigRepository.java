package com.example.didit.app.repository;

import com.example.didit.app.entity.CountryRiskConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRiskConfigRepository extends JpaRepository<CountryRiskConfig, Long> {

    CountryRiskConfig findByCountryIgnoreCase(String country);

}
