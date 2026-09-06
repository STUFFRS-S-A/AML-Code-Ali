package com.appopay.visa.repository;
import com.appopay.visa.entity.PanData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanDataRepository extends JpaRepository<PanData, Long> {
    // Custom query methods can be defined here if needed
}