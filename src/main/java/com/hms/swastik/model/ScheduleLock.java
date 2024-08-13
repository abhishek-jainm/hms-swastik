package com.hms.swastik.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ScheduleLock {

    @Id
    private Long id;

    @Column(name = "last_run_date")
    private LocalDate lastRunDate;
}

