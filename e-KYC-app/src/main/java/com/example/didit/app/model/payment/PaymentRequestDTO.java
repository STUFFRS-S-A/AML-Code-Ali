package com.example.didit.app.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @JsonProperty("phn_no_cc")
    private String phnNoCc;

    @JsonProperty("mobile_num")
    private String mobileNum;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("card_ref_num")
    private String cardRefNum;
}
