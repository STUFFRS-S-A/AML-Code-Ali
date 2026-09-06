package com.appopay.visa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseDTO {

    @JsonProperty("error")
    private boolean error;

    @JsonProperty("results")
    private List<Result> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        @JsonProperty("name")
        private String name;

        @JsonProperty("matchCount")
        private Integer matchCount;

    }
}
