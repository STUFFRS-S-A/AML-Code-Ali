package com.appopay.aml.repository;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.IdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdRepository extends JpaRepository<IdCard, Long> {

//    IdCard findByCustomers_Id(Long customerId);
}
