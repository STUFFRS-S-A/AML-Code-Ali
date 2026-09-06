package com.appopay.visa.model;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class PaginatedOfacDto {

    List<OFACDTO> data;
    Long documents;

}
