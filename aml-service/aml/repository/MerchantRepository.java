package com.appopay.aml.repository;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
