package com.hms.swastik.repo;

import com.hms.swastik.model.DailyCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyCountRepo extends JpaRepository<DailyCount, Long> {
    Optional<DailyCount> findByCountDate(LocalDate date);
}
