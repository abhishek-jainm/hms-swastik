package com.hms.swastik.controller;

import com.hms.swastik.model.Patient;
import com.hms.swastik.service.DailyCountService;
import com.hms.swastik.service.PatientService;
import com.hms.swastik.service.SequenceNumberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PatientController {
    private final PatientService patientService;
    private final DailyCountService dailyCountService;
    public PatientController(PatientService patientService,DailyCountService dailyCountService){
        this.patientService=patientService;
        this.dailyCountService=dailyCountService;
    }
    @PostMapping(value = "/add-patient")
    public String addPatient(@ModelAttribute("patient") Patient patient){
        Patient patient1=patientService.addPateint(patient);
        return "redirect:/print-prescription/"+patient1.getId();
    }

    @GetMapping(value = "/get-all-patient")
    public List<Patient> getAllPatient(){
        return patientService.getAllPatient();
    }

    @GetMapping(value = "/print-prescription/{id}")
    public String printPatientPrescription(@PathVariable("id") Long id, Model model){
        Patient patient = patientService.getPatientById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = patient.getCreatedAt().format(formatter);
        int sequenceNumber = dailyCountService.getNextCount();

        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("patient", patient);
        model.addAttribute("sequenceNumber", sequenceNumber);
        return "prescription";
    }

    @GetMapping(value = "/generateBill/{id}")
    public String generatePatientBill(@PathVariable("id") Long id, Model model){
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "billDetails";
    }

    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> generateReport(
            @RequestParam String patientName,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        ByteArrayInputStream in = patientService.generateReport( start, end);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=patient_report.xlsx");


        return new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
    }

}
