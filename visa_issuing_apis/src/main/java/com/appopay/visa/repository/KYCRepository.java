package com.appopay.visa.repository;

import com.appopay.visa.entity.Customers;
import com.appopay.visa.entity.KYCInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KYCRepository extends JpaRepository<KYCInfo, Long> {

    Optional<KYCInfo> findBySessionId(String sessionId);
}
