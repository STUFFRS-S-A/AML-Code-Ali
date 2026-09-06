package com.appopay.visa.service;


import com.appopay.visa.entity.PanData;
import com.appopay.visa.model.VisaApiResponse;
import com.appopay.visa.repository.PanDataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.upi.security.jose.JwsUtil;
import com.upi.security.jose.UpiJoseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class PanService {

    @Autowired
    private PanDataRepository panDataRepository;
    public String generatePan() throws Exception {
        List<PanData> panDataList = panDataRepository.findAll();

        if(!(panDataList.size() == 1)){
            throw new Exception("Pan data corrupted");
        }

        PanData panData = panDataList.get(0);
        if(panData.getRandomNumber() == 999999999L)
            throw new Exception("max limit reached");

        Long randomNumber = panData.getRandomNumber();
        Long incrementedNumber = ++randomNumber;
        String formattedNUmber = String.format("%09d", incrementedNumber);
        String pan = panData.getBin().toString() + panData.getProductCode().toString() + formattedNUmber;

        panData.setRandomNumber(incrementedNumber);
        panDataRepository.save(panData);
        return pan;

    }
}