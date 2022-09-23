package com.lunatech.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BaconSeparation {
    private int degreeOfSeparation;

    public BaconSeparation(int degree) {
        this.degreeOfSeparation = degree;
    }
}
