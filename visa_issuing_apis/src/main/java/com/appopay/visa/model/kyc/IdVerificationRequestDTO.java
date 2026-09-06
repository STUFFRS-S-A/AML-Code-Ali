package com.appopay.visa.model.kyc;

import org.springframework.web.multipart.MultipartFile;

public class IdVerificationRequestDTO {
    MultipartFile front;
    MultipartFile back;
    Boolean perfrom_liveliness;
    Integer minimum_age;
    Action expiration_date_not_detected_action;
    Action invalid_mrz_action;
    Action inconsistent_data_action;

    public MultipartFile getFront() {
        return front;
    }

    public MultipartFile getBack() {
        return back;
    }

    public Boolean getPerfrom_liveliness() {
        return perfrom_liveliness;
    }

    public Integer getMinimum_age() {
        return minimum_age;
    }

    public Action getExpiration_date_not_detected_action() {
        return expiration_date_not_detected_action;
    }

    public Action getInvalid_mrz_action() {
        return invalid_mrz_action;
    }

    public Action getInconsistent_data_action() {
        return inconsistent_data_action;
    }

    public void setFront(MultipartFile front) {
        this.front = front;
    }

    public void setBack(MultipartFile back) {
        this.back = back;
    }

    public void setPerfrom_liveliness(Boolean perfrom_liveliness) {
        this.perfrom_liveliness = perfrom_liveliness;
    }

    public void setMinimum_age(Integer minimum_age) {
        this.minimum_age = minimum_age;
    }

    public void setExpiration_date_not_detected_action(Action expiration_date_not_detected_action) {
        this.expiration_date_not_detected_action = expiration_date_not_detected_action;
    }

    public void setInvalid_mrz_action(Action invalid_mrz_action) {
        this.invalid_mrz_action = invalid_mrz_action;
    }

    public void setInconsistent_data_action(Action inconsistent_data_action) {
        this.inconsistent_data_action = inconsistent_data_action;
    }
}
