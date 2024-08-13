package com.hms.swastik.repo;

import com.hms.swastik.model.ScheduleLock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleLockRepo extends JpaRepository<ScheduleLock, Long> {
}

