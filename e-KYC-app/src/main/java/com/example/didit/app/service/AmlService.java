package com.example.didit.app.service;

import com.example.didit.app.entity.CountryRiskConfig;

import com.example.didit.app.repository.CountryRiskConfigRepository;
import com.example.didit.app.repository.UserMasterRepository;
import com.example.didit.app.security.AuthManager;
import com.example.didit.app.security.CustomUserDetails;
import com.example.didit.app.security.JwtUtil;
import com.example.didit.app.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AmlService {

    private final CountryRiskConfigRepository countryRiskConfigRepository;

    public String calculareNationalityRisk(String countryCode){
        CountryRiskConfig countryRiskConfig= countryRiskConfigRepository.findByCountryIgnoreCase(countryCode);
        if(countryRiskConfig!=null){
            Long riskScoreNationality = Long.valueOf(countryRiskConfig.getRiskScoreNationality());
            if(riskScoreNationality<=80) return "LOW";
            if(riskScoreNationality<=90) return "MEDIUM";
            if(riskScoreNationality<100) return "HIGH";
        }
        return null;
    }
}
