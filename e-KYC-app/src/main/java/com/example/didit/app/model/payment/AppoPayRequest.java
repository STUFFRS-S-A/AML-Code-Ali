package com.example.didit.app.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppoPayRequest {
    @JsonProperty("digest_info")
    private String digestInfo;
    
    @JsonProperty("device_info")
    private DeviceInfo deviceInfo;
    
    @JsonProperty("request_key")
    private RequestKey requestKey;
    
    @JsonProperty("request_data")
    private RequestData requestData;

    @Data
    @Builder
    public static class DeviceInfo {
        private String name;
        private String manufacturer;
        private String model;
        private String version;
        private String os;
    }

    @Data
    @Builder
    public static class RequestKey {
        @JsonProperty("request_type")
        private String requestType;
        @JsonProperty("request_id")
        private String requestId;
    }

    @Data
    @Builder
    public static class RequestData {
        @JsonProperty("request_id")
        private String requestId;
        @JsonProperty("request_date")
        private String requestDate;
        @JsonProperty("request_time")
        private String requestTime;
        @JsonProperty("inst_id")
        private String instId;
        @JsonProperty("entity_type")
        private String entityType;
        @JsonProperty("phn_no_cc")
        private String phnNoCc;
        @JsonProperty("mobile_num")
        private String mobileNum;
        @JsonProperty("otp")
        private String otp;
        @JsonProperty("card_ref_num")
        private String cardRefNum;
        @JsonProperty("iso_req_data")
        private IsoReqData isoReqData;
    }

    @Data
    @Builder
    public static class IsoReqData {
        private String mti;
        private String fld2;
        private String fld3;
        private String fld4;
        private String fld14;
        private String fld18;
        private String fld19;
        private String fld22;
        private String fld41;
        private String fld42;
        private String fld43;
        private String fld44;
        private String fld49;
        private String fld51;
        private String fld64;
    }
}
