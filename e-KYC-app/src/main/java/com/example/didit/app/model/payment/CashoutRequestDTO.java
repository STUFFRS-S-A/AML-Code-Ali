package com.example.didit.app.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CashoutRequestDTO {
    @JsonProperty("fld2")
    private String fld2;

    @JsonProperty("fld4")
    private String fld4;

    @JsonProperty("fld14")
    private String fld14;

    @JsonProperty("fld64")
    private String fld64;
}
