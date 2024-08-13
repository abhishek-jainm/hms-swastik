package com.hms.swastik.repo;

import com.hms.swastik.model.PatientBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientBillRepo extends JpaRepository<PatientBill,Long> {
}
