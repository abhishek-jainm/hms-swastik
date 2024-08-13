package com.hms.swastik.model;

import lombok.Data;

import java.util.List;
@Data
public class BillRequest {
    private Long patientId;
    private String patientName;
    private String billNo;
    private String drName; // Including doctor's name
    private List<BillDetails> list;
    private double total;
}
