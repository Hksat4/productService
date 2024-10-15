package com.MyProduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerData {
    private String customerId;
    private String contractId;
    private String geozone;
    private String teamcode;
    private String projectcode;
    private int buildDuration; // Converted to int
}
