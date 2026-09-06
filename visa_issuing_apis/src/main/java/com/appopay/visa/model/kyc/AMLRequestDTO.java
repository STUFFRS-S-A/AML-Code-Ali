package com.appopay.visa.model.kyc;

public class AMLRequestDTO {

    String full_name;
    String date_of_birth;
    String nationality;

    String document_number;

    Integer aml_score_approve_threshold;

    Boolean include_adverse_media;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDocument_number() {
        return document_number;
    }

    public void setDocument_number(String document_number) {
        this.document_number = document_number;
    }

    public Integer getAml_score_approve_threshold() {
        return aml_score_approve_threshold;
    }

    public void setAml_score_approve_threshold(Integer aml_score_approve_threshold) {
        this.aml_score_approve_threshold = aml_score_approve_threshold;
    }

    public Boolean getInclude_adverse_media() {
        return include_adverse_media;
    }

    public void setInclude_adverse_media(Boolean include_adverse_media) {
        this.include_adverse_media = include_adverse_media;
    }
}
