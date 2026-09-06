package com.appopay.visa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwillioResponseDTO {

    @JsonProperty("account_sid")
    private String account_sid;

    @JsonProperty("api_version")
    private String api_version;

    @JsonProperty("body")
    private String body;

    @JsonProperty("date_created")
    private String date_created;

    @JsonProperty("date_sent")
    private String date_sent;

    @JsonProperty("date_updated")
    private String date_updated;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("error_code")
    private String error_code;

    @JsonProperty("error_message")
    private String error_message;

    @JsonProperty("from")
    private String from;

    @JsonProperty("messaging_service_sid")
    private String messaging_service_sid;

    @JsonProperty("num_media")
    private String num_media;

    @JsonProperty("num_segments")
    private String num_segments;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_unit")
    private String price_unit;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("status")
    private String status;

    @JsonProperty("to")
    private String to;

    @JsonProperty("subresource_uris")
    private SubresourceUris subresourceUris;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubresourceUris {
        @JsonProperty("media")
        private String media;
    }

    @JsonProperty("uri")
    private String uri;

}
