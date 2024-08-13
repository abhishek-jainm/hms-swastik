package com.hms.swastik.service;

import com.hms.swastik.model.Patient;
import com.hms.swastik.repo.PatientRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository){
        this.patientRepository=patientRepository;
    }
    public Patient addPateint(Patient pateint) {
        pateint.setCreatedAt(LocalDate.now());
        pateint.setFollowUpCount(1l);
        patientRepository.save(pateint);
        return pateint;
    }

    public List<Patient> getAllPatient(){
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id){
        return patientRepository.findById(id).get();
    }


    public List<Patient> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return patientRepository.findAll();
    }

    public ByteArrayInputStream generatePatientReport(LocalDate toDate, LocalDate endDate) {
        ByteArrayInputStream in = this.generateReport( toDate, endDate);
        return in;
    }

    public ByteArrayInputStream generateReport(LocalDate toDate, LocalDate endDate) {
        List<Patient> reports = patientRepository.findByCreatedAtBetween(toDate, toDate);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Patient Report");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Patient ID");
            headerRow.createCell(1).setCellValue("Patient Name");
            headerRow.createCell(2).setCellValue("Age");
            headerRow.createCell(3).setCellValue("Consultation");
            headerRow.createCell(4).setCellValue("Date");
            headerRow.createCell(5).setCellValue("Mobile Number");
            headerRow.createCell(6).setCellValue("Address");

            int rowNum = 1;
            for (Patient report : reports) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(report.getId());
                row.createCell(1).setCellValue(report.getName());
                row.createCell(2).setCellValue(report.getAge());
                row.createCell(3).setCellValue(report.getConsultation());
                row.createCell(4).setCellValue(report.getCreatedAt().toString());
                row.createCell(5).setCellValue(report.getMobileNo());
                row.createCell(6).setCellValue(report.getAddress());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }
}
