package com.appopay.visa.repository;

import com.appopay.visa.entity.OFACEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OFACRepository extends JpaRepository<OFACEntity, Long> {

    OFACEntity findByName(String name);
}
