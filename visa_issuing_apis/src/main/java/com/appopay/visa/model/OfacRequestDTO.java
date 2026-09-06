package com.appopay.visa.model;

import lombok.Data;

import java.util.List;

@Data
public class OfacRequestDTO {

    private String apiKey;
    private int minScore;
    private List<String> sources;
    private List<String> types;
    private List<CaseDTO> cases;

    @Data
    public static class CaseDTO {
        private String name;
        private String type;
    }

}
