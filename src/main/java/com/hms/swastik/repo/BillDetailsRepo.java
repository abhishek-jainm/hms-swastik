package com.hms.swastik.repo;

import com.hms.swastik.model.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDetailsRepo extends JpaRepository<BillDetails,Long> {
    List<BillDetails> findByBillNo(Long id);
}
