package com.hms.swastik.repo;

import com.hms.swastik.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findByCreatedAtBetween(LocalDate toDate, LocalDate endDate);
}
