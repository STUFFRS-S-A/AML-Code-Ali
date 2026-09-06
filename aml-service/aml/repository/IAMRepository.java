package com.appopay.aml.repository;

import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.IAM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAMRepository extends JpaRepository<IAM, Long> {

    IAM findByUserName(String Username);

    boolean existsByUserName(String username);

    void deleteByUserName(String username);
}
