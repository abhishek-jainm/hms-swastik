package com.hms.swastik.controller;

import com.hms.swastik.model.BillDetails;
import com.hms.swastik.model.BillRequest;
import com.hms.swastik.model.Patient;
import com.hms.swastik.model.PatientBill;
import com.hms.swastik.service.BillService;
import com.hms.swastik.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class BillController {
    private final BillService billService;
    private final PatientService patientService;

    public BillController(BillService billService,PatientService patientService){
        this.billService=billService;
        this.patientService=patientService;
    }

    @PostMapping(value = "/save-bill")
    public PatientBill saveBill(@RequestBody BillRequest billRequest) {
        return billService.saveBillDetails(billRequest);
    }

}
