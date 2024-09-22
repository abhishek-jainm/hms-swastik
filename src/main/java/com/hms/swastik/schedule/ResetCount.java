package com.hms.swastik.schedule;

import com.hms.swastik.model.DailyCount;
import com.hms.swastik.model.ScheduleLock;
import com.hms.swastik.repo.DailyCountRepo;
import com.hms.swastik.repo.ScheduleLockRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ResetCount {

    private static final Long LOCK_ID = 1L;

    private final DailyCountRepo dailyCountRepository;
    private final ScheduleLockRepo scheduleLockRepository;

    ResetCount(DailyCountRepo dailyCountRepository,ScheduleLockRepo scheduleLockRepository){
        this.dailyCountRepository=dailyCountRepository;
        this.scheduleLockRepository=scheduleLockRepository;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    @Transactional
    public void resetDailyCount() {
        LocalDate today = LocalDate.now();
        Optional<ScheduleLock> lockOptional = scheduleLockRepository.findById(LOCK_ID);

        if (lockOptional.isPresent() && !lockOptional.get().getLastRunDate().isBefore(today)) {
            return;
        }

        DailyCount dailyCount = dailyCountRepository.findByCountDate(today)
                .orElseGet(() -> {
                    DailyCount newCount = new DailyCount();
                    newCount.setCountDate(today);
                    return newCount;
                });
        dailyCount.setCount(0);
        dailyCountRepository.save(dailyCount);

        ScheduleLock lock = lockOptional.orElseGet(() -> {
            ScheduleLock newLock = new ScheduleLock();
            newLock.setId(LOCK_ID);
            return newLock;
        });
        lock.setLastRunDate(today);
        scheduleLockRepository.save(lock);
    }
}

