package com.hms.swastik.service;

import com.hms.swastik.model.BillDetails;
import com.hms.swastik.model.BillRequest;
import com.hms.swastik.model.PatientBill;
import com.hms.swastik.repo.BillDetailsRepo;
import com.hms.swastik.repo.PatientBillRepo;
import com.hms.swastik.repo.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {
    private final BillDetailsRepo billDetailsRepo;
    private final PatientBillRepo patientBillRepo;
    private final PatientRepository patientRepository;
    public BillService(BillDetailsRepo billDetailsRepo,PatientBillRepo patientBillRepo,PatientRepository patientRepository){
        this.patientBillRepo=patientBillRepo;
        this.billDetailsRepo=billDetailsRepo;
        this.patientRepository=patientRepository;

    }

    public PatientBill saveBillDetails(BillRequest billRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = LocalDate.now().format(formatter);
        PatientBill patientBill=PatientBill.builder().
                patientId(billRequest.getPatientId())
                .drName(billRequest.getDrName())
                .total(billRequest.getTotal())
                .createdAt(formattedDate)
                .patientName(billRequest.getPatientName()).build();
        PatientBill patientBill1=patientBillRepo.save(patientBill);
        List<BillDetails> updatedBillDetailsList = billRequest.getList().stream()
                .map(billDetails -> {
                    billDetails.setBillNo(patientBill1.getId()); // Set the billNo
                    return billDetails;
                })
                .collect(Collectors.toList());
        billDetailsRepo.saveAll(updatedBillDetailsList);
        return patientBill1;
    }

    public PatientBill getBillDetails(Long id) {
       return patientBillRepo.findById(id).get();
    }

    public List<BillDetails> getBillDetailsList(Long id) {
        return billDetailsRepo.findByBillNo(id);
    }

    public List<PatientBill> getAllBills() {
        return patientBillRepo.findAll();
    }
}
