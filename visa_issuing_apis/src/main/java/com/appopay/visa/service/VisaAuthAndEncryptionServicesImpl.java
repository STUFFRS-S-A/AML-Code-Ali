package com.appopay.visa.service;


import com.appopay.visa.model.VisaApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.upi.security.jose.JwsUtil;
import com.upi.security.jose.UpiJoseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Component
public class VisaAuthAndEncryptionServicesImpl {

    public static final String SUCCESS = "success";
    private static final Logger log = LoggerFactory
            .getLogger(VisaAuthAndEncryptionServicesImpl.class);
    private static PrivateKey appopayJwsPrivateK;


    @Value("${appopay.jws.private.key}")
    private String appopayJwsPrivateKey;

    @Value("${jwe.public.key}")
    private String jwePublicKey;

    public VisaApiResponse getJWSToken(String requestPath, String visaRequest) {
        try {

            byte[] privateKeyBytes = Base64.getDecoder().decode(appopayJwsPrivateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory privateKeyFactory;
            try {
                privateKeyFactory = KeyFactory.getInstance("RSA");
                appopayJwsPrivateK = privateKeyFactory.generatePrivate(pkcs8KeySpec);
            } catch (Exception e) {
                log.error("privateKey get error" + e);
            }

            String detachedJwsContent = "";
            String jwsContent = "";
            /************************ Generate JWS information ***************************/
            try {
                // Generate JWS information
                Gson gs = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
                log.info("jws json=" + gs.toJson(visaRequest));
                jwsContent = UpiJoseUtils
                        .genJws(gs.toJson(visaRequest), "20211027000000", requestPath,
                                "39990157", appopayJwsPrivateK);
                detachedJwsContent = JwsUtil.detachedJwsContent(jwsContent);
                log.info("detached-jws-content : " + detachedJwsContent);
            } catch (Exception e) {
                log.error("genJws error" + e);
            }

            return new VisaApiResponse(HttpStatus.OK, SUCCESS, jwsContent);
        } catch (Exception e) {
            return null;
        }

    }

    public VisaApiResponse generateJweToken(String payload) {
        try {
            // Replace these with your actual keys
            String publicKeyStr = jwePublicKey;
            String privateKeyStr = appopayJwsPrivateKey;

            PublicKey publicKey = loadPublicKey(publicKeyStr);
            PrivateKey privateKey = loadPrivateKey(privateKeyStr);

            // Convert the RSA public key to JWK format
            RSAKey rsaJWK = new RSAKey.Builder((RSAPublicKey) publicKey).build();

            // Create the JWT claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .claim("payload", payload)
                    .build();

            // Create the signed JWT
            SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(), claimsSet);

            // Create an RSA signer with the private key
            JWSSigner signer = new RSASSASigner(privateKey);
            signedJWT.sign(signer);

            // Encrypt the JWT
            JWEObject jweObject = new JWEObject(
                    new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                            .contentType("JWT") // required to signal nested JWT
                            .build(),
                    new Payload(signedJWT)
            );
            jweObject.encrypt(new RSAEncrypter(rsaJWK));

            // Serialize to compact JWE form
            return new VisaApiResponse(HttpStatus.OK, SUCCESS, jweObject.serialize());
        } catch (Exception e) {
            log.error("jwe get error" + e);
            throw new RuntimeException(e);
        }
    }

    private static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        byte[] encoded = Base64.getDecoder().decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }

    private static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        byte[] encoded = Base64.getDecoder().decode(privateKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }
}