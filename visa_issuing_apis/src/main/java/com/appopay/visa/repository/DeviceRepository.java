package com.appopay.visa.repository;

import com.appopay.visa.entity.DeviceEntity;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    @Query("SELECT e FROM DeviceEntity e WHERE e.updatedAt < :time AND e.status = :status")
    List<DeviceEntity> findAllWithUpdatedTimeBeforeAndStatus(@Param("time") Instant time, @Param("status") String status);

    DeviceEntity findByDeviceId(String deviceId);
    Optional<DeviceEntity> findByMobileNo(String mobileNo);
    Optional<DeviceEntity> findByMobileNoAndDeviceId(String mobileNo,String deviceId);
}
