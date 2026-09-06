package com.appopay.aml.repository;

import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.TransactionRiskConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRiskConfigRepository extends JpaRepository<TransactionRiskConfig, Long> {


}
