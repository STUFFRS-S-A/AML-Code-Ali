package com.appopay.visa.repository;

import com.appopay.visa.entity.CustomerToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerTokenRepository extends JpaRepository<CustomerToken, Long> {
    Optional<CustomerToken> findById(String id);
}
