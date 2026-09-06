package com.appopay.aml.repository;

import com.appopay.aml.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

    Optional<Customers> findById(String id);

    void deleteById(String id);

    Boolean existsById(String id);

}
