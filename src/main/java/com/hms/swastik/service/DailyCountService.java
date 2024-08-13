package com.hms.swastik.service;

import com.hms.swastik.model.DailyCount;
import com.hms.swastik.repo.DailyCountRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DailyCountService {


    private final DailyCountRepo dailyCountRepo;
    DailyCountService(DailyCountRepo dailyCountRepo){
        this.dailyCountRepo=dailyCountRepo;
    }

    @Transactional
    public int getNextCount() {
        LocalDate today = LocalDate.now();
        DailyCount dailyCount = dailyCountRepo.findByCountDate(today)
                .orElseGet(() -> {
                    DailyCount newCount = new DailyCount();
                    newCount.setCount(0);
                    newCount.setCountDate(today);
                    return newCount;
                });

        dailyCount.setCount(dailyCount.getCount() + 1);
        dailyCountRepo.save(dailyCount);

        return dailyCount.getCount();
    }
}

