package com.appopay.visa.service;

import com.appopay.visa.entity.OTPEntiy;
import com.appopay.visa.Exception.CustomException;
import com.appopay.visa.model.TwillioRequestDTO;
import com.appopay.visa.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class OTPService {

    private final Random random = new Random();
    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private TwillioService twillioService;

    public String sendOTP(TwillioRequestDTO request) {
        try {
            String otp = generateUniqueOtp(request.getPhoneCode() + request.getMobileNumber());
            twillioService.sendSMS(request.getPhoneCode() + request.getMobileNumber(), otp + "," + request.getHashKey());
            return otp;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public String validateOTP(String toNumber, String otp) {
        OTPEntiy otpEntiy = otpRepository.findByNumberAndIsValid(toNumber, true);
        Instant tenMinutesAgo = Instant.now().minus(Duration.ofMinutes(10));
        if (otpEntiy == null || !otpEntiy.getOtp().equals(otp)) {
            return "false";
        }
        if (otpEntiy.getCreatedAt().isBefore(tenMinutesAgo)) {
            return "false";
        }


        return "true";

    }


    public String generateUniqueOtp(String toNumber) {
        Instant oneHour = Instant.now();
        int newOtp;
        do {
            newOtp = 100000 + random.nextInt(900000); // Generates a number between 1000 and 9999
        } while (!isOtpUnique(newOtp, oneHour));
        invalidateOtp(toNumber);
        OTPEntiy otpEntiy = new OTPEntiy(toNumber, String.valueOf(newOtp), true);
        otpRepository.save(otpEntiy);
        return String.valueOf(newOtp);
    }

    private boolean isOtpUnique(int otp, Instant instant) {
        return otpRepository.findAllByOtpAndCreatedAtAfter(String.valueOf(otp), instant).isEmpty();
    }

    private void invalidateOtp(String toNumber) {
        List<OTPEntiy> list = otpRepository.findAllByNumberAndIsValid(toNumber, true);

        for (OTPEntiy otpEntiy : list) {
            otpEntiy.setValid(false);
            otpRepository.save(otpEntiy);
        }
    }

}
