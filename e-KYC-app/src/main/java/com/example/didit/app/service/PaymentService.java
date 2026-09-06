package com.example.didit.app.service;

import com.example.didit.app.model.payment.AppoPayRequest;
import com.example.didit.app.model.payment.CashoutRequestDTO;
import com.example.didit.app.model.payment.PaymentRequestDTO;
import com.example.didit.app.util.TokenGenerator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;

    public PaymentService() {
        this.restTemplate = new RestTemplate();
    }

    public Object validateCustomer(PaymentRequestDTO dto) throws Exception {
        String url = "https://api.appopay.com/apigateway";

        String requestId = String.valueOf(System.currentTimeMillis());

        // 2. Generate request_date (DDMMYYYY) and request_time (HHMMSS) - "now"
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        // 3. Build the auth_token for these exact three values
        String authToken = TokenGenerator.buildAuthToken(requestId, requestDate, requestTime);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-channel", "web");
        headers.set("x-api-lang", "us/eng");
        headers.set("x-api-version", "v.0.0.1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        headers.set("Cookie", "HWWAFSESID=d4a827373083a5e465; HWWAFSESTIME=1784224773223");

        AppoPayRequest.DeviceInfo deviceInfo = AppoPayRequest.DeviceInfo.builder()
                .name("NA")
                .manufacturer("NA")
                .model("NA")
                .version("NA")
                .os("NA")
                .build();

        AppoPayRequest.RequestKey requestKey = AppoPayRequest.RequestKey.builder()
                .requestType("kyc_portal_cust_validation")
                .requestId("NA")
                .build();

        AppoPayRequest.RequestData requestData = AppoPayRequest.RequestData.builder()
                .requestId(requestId)
                .requestDate(requestDate)
                .requestTime(requestTime)
                .instId("AP")
                .phnNoCc(dto.getPhnNoCc())
                .mobileNum(dto.getMobileNum())
                .build();

        AppoPayRequest requestBody = AppoPayRequest.builder()
                .digestInfo("NA")
                .deviceInfo(deviceInfo)
                .requestKey(requestKey)
                .requestData(requestData)
                .build();

        HttpEntity<AppoPayRequest> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        return response.getBody();
    }

    public Object sendOtp(PaymentRequestDTO dto) throws Exception {
        String url = "https://api.appopay.com/apigateway";

        String requestId = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        String authToken = TokenGenerator.buildAuthToken(requestId, requestDate, requestTime);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-channel", "mob"); // Changed to mob as per curl
        headers.set("x-api-lang", "us/eng");
        headers.set("x-api-version", "v.0.0.1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        headers.set("Cookie", "HWWAFSESID=d4a827373083a5e465; HWWAFSESTIME=1784224773223");

        AppoPayRequest.DeviceInfo deviceInfo = AppoPayRequest.DeviceInfo.builder()
                .name("NA")
                .manufacturer("NA")
                .model("NA")
                .version("NA")
                .os("NA")
                .build();

        AppoPayRequest.RequestKey requestKey = AppoPayRequest.RequestKey.builder()
                .requestType("kyc_portal_send_otp")
                .requestId("NA")
                .build();

        AppoPayRequest.RequestData requestData = AppoPayRequest.RequestData.builder()
                .requestId(requestId)
                .requestDate(requestDate)
                .requestTime(requestTime)
                .instId("AP")
                .entityType("@CUSTOMER")
                .phnNoCc(dto.getPhnNoCc())
                .mobileNum(dto.getMobileNum())
                .build();

        AppoPayRequest requestBody = AppoPayRequest.builder()
                .digestInfo("NA")
                .deviceInfo(deviceInfo)
                .requestKey(requestKey)
                .requestData(requestData)
                .build();

        HttpEntity<AppoPayRequest> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        return response.getBody();
    }

    public Object resendOtp(PaymentRequestDTO dto) throws Exception {
        String url = "https://api.appopay.com/apigateway";

        String requestId = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        String authToken = TokenGenerator.buildAuthToken(requestId, requestDate, requestTime);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-channel", "mob");
        headers.set("x-api-lang", "us/eng");
        headers.set("x-api-version", "v.0.0.1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        // The user didn't have the Cookie header in the resend curl, but it was in the others. I will leave it out as per the curl, or include it since it was there before? The user explicitly did not include the Cookie header in the provided curl for resend OTP. Wait, actually I will include it to match sendOtp or I will exclude it. Let's just exclude the Cookie header since it's not in the new curl.
        // Actually, looking at the previous curls, sometimes they had it, sometimes not. Let me just omit it to match exactly what they gave.
        
        AppoPayRequest.DeviceInfo deviceInfo = AppoPayRequest.DeviceInfo.builder()
                .name("NA")
                .manufacturer("NA")
                .model("NA")
                .version("NA")
                .os("NA")
                .build();

        AppoPayRequest.RequestKey requestKey = AppoPayRequest.RequestKey.builder()
                .requestType("kyc_portal_resend_otp")
                .requestId("NA")
                .build();

        AppoPayRequest.RequestData requestData = AppoPayRequest.RequestData.builder()
                .requestId(requestId)
                .requestDate(requestDate)
                .requestTime(requestTime)
                .instId("AP")
                .entityType("@CUSTOMER")
                .phnNoCc(dto.getPhnNoCc())
                .mobileNum(dto.getMobileNum())
                .build();

        AppoPayRequest requestBody = AppoPayRequest.builder()
                .digestInfo("NA")
                .deviceInfo(deviceInfo)
                .requestKey(requestKey)
                .requestData(requestData)
                .build();

        HttpEntity<AppoPayRequest> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        return response.getBody();
    }

    public Object custEnquiry(PaymentRequestDTO dto) throws Exception {
        String url = "https://api.appopay.com/apigateway";

        String requestId = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        // From curl, authorization is a static token or generated one? The user says "req id , date and time will be apssed dyanmically , rest va;lues will be passed LIKE INTHE CURL exept the ones you took in the req body". But in previous APIs `TokenGenerator.buildAuthToken` was used. Let's use the static token as per the curl, or the dynamic one? "see other three apis and follow the structure". I will use the dynamic token generator as that's the established structure in the file.
        String authToken = TokenGenerator.buildAuthToken(requestId, requestDate, requestTime);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-channel", "web");
        headers.set("x-api-lang", "us/eng");
        headers.set("x-api-version", "v.0.0.1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        headers.set("Cookie", "HWWAFSESID=92415736afc49694d5; HWWAFSESTIME=1784363483181");

        AppoPayRequest.DeviceInfo deviceInfo = AppoPayRequest.DeviceInfo.builder()
                .name("NA")
                .manufacturer("NA")
                .model("NA")
                .version("NA")
                .os("NA")
                .build();

        AppoPayRequest.RequestKey requestKey = AppoPayRequest.RequestKey.builder()
                .requestType("kyc_portal_cust_enq")
                .requestId("NA")
                .build();

        AppoPayRequest.RequestData requestData = AppoPayRequest.RequestData.builder()
                .requestId(requestId)
                .requestDate(requestDate)
                .requestTime(requestTime)
                .instId("AP")
                .phnNoCc(dto.getPhnNoCc())
                .mobileNum(dto.getMobileNum())
                .otp(dto.getOtp())
                .build();

        AppoPayRequest requestBody = AppoPayRequest.builder()
                .digestInfo("NA")
                .deviceInfo(deviceInfo)
                .requestKey(requestKey)
                .requestData(requestData)
                .build();

        HttpEntity<AppoPayRequest> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        return response.getBody();
    }

    public Object decryptCardNum(PaymentRequestDTO dto) throws Exception {
        String url = "https://api.appopay.com/apigateway";

        String requestId = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        String authToken = TokenGenerator.buildAuthToken(requestId, requestDate, requestTime);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-channel", "web");
        headers.set("x-api-lang", "us/eng");
        headers.set("x-api-version", "v.0.0.1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        headers.set("Cookie", "HWWAFSESID=92415736afc49694d5; HWWAFSESTIME=1784363483181");

        AppoPayRequest.DeviceInfo deviceInfo = AppoPayRequest.DeviceInfo.builder()
                .name("NA")
                .manufacturer("NA")
                .model("NA")
                .version("NA")
                .os("NA")
                .build();

        AppoPayRequest.RequestKey requestKey = AppoPayRequest.RequestKey.builder()
                .requestType("kyc_portal_decrypt_card_num")
                .requestId("NA")
                .build();

        AppoPayRequest.RequestData requestData = AppoPayRequest.RequestData.builder()
                .requestId(requestId)
                .requestDate(requestDate)
                .requestTime(requestTime)
                .instId("AP")
                .cardRefNum(dto.getCardRefNum())
                .build();

        AppoPayRequest requestBody = AppoPayRequest.builder()
                .digestInfo("NA")
                .deviceInfo(deviceInfo)
                .requestKey(requestKey)
                .requestData(requestData)
                .build();

        HttpEntity<AppoPayRequest> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        return response.getBody();
    }

    public Object cashout(CashoutRequestDTO dto) throws Exception {
        String url = "https://api.appopay.com/apigateway";

        String requestId = String.valueOf(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();
        String requestDate = now.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String requestTime = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-channel", "web");
        headers.set("x-api-lang", "us/eng");
        headers.set("x-api-version", "v.0.0.1");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "HWWAFSESID=92415736afc49694d5; HWWAFSESTIME=1784363483181");

        AppoPayRequest.DeviceInfo deviceInfo = AppoPayRequest.DeviceInfo.builder()
                .name("NA")
                .manufacturer("NA")
                .model("NA")
                .version("NA")
                .os("NA")
                .build();

        AppoPayRequest.RequestKey requestKey = AppoPayRequest.RequestKey.builder()
                .requestType("kycportal_cashout")
                .requestId("NA")
                .build();

        AppoPayRequest.IsoReqData isoReqData = AppoPayRequest.IsoReqData.builder()
                .mti("0100")
                .fld2(dto.getFld2())
                .fld3("010001")
                .fld4(dto.getFld4())
                .fld14(dto.getFld14())
                .fld18("0601")
                .fld19("356")
                .fld22("010")
                .fld41("01778800")
                .fld42("921059158120177")
                .fld43("Kyc merchant             Bengaluru   IND")
                .fld44("8501801808")
                .fld49("356")
                .fld51("356")
                .fld64(dto.getFld64())
                .build();

        AppoPayRequest.RequestData requestData = AppoPayRequest.RequestData.builder()
                .requestId(requestId)
                .requestDate(requestDate)
                .requestTime(requestTime)
                .instId("AP")
                .isoReqData(isoReqData)
                .build();

        AppoPayRequest requestBody = AppoPayRequest.builder()
                .digestInfo("NA")
                .deviceInfo(deviceInfo)
                .requestKey(requestKey)
                .requestData(requestData)
                .build();

        HttpEntity<AppoPayRequest> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        return response.getBody();
    }
}
