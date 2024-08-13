package com.hms.swastik.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class SequenceNumberService {
    private int sequenceNumber = 1;
    private LocalDate lastDate = LocalDate.now();

    public synchronized int getNextSequenceNumber() {
        LocalDate currentDate = LocalDate.now();
        if (!currentDate.isEqual(lastDate)) {
            sequenceNumber = 1;
            lastDate = currentDate;
        }
        return sequenceNumber++;
    }
}

