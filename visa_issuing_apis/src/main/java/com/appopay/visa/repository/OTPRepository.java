package com.appopay.visa.repository;

import com.appopay.visa.entity.OTPEntiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntiy, Long> {

    List<OTPEntiy> findAllByOtpAndCreatedAtAfter(String otp, Instant instant);

    List<OTPEntiy> findAllByNumberAndIsValid(String number, boolean isValid);
    OTPEntiy findByNumberAndIsValid(String number, boolean isValid);

}
