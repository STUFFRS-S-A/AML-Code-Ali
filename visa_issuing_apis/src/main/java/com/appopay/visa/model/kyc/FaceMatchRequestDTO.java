package com.appopay.visa.model.kyc;

import org.springframework.web.multipart.MultipartFile;

public class FaceMatchRequestDTO {
    MultipartFile user_image;
    MultipartFile ref_image;
    Integer face_match_score_decline_threshold;
    Boolean rotate_image;

    public MultipartFile getUser_image() {
        return user_image;
    }

    public void setUser_image(MultipartFile user_image) {
        this.user_image = user_image;
    }

    public MultipartFile getRef_image() {
        return ref_image;
    }

    public void setRef_image(MultipartFile ref_image) {
        this.ref_image = ref_image;
    }

    public Integer getFace_match_score_decline_threshold() {
        return face_match_score_decline_threshold;
    }

    public void setFace_match_score_decline_threshold(Integer face_match_score_decline_threshold) {
        this.face_match_score_decline_threshold = face_match_score_decline_threshold;
    }

    public Boolean getRotate_image() {
        return rotate_image;
    }

    public void setRotate_image(Boolean rotate_image) {
        this.rotate_image = rotate_image;
    }
}
