package com.hms.swastik.model;

import lombok.Data;

import java.util.List;
@Data
public class BillRequest {
    private Long patientId;
    private String patientName;
    private String billNo;
    private String drName;
    private List<BillDetails> list;
    private double total;
}
