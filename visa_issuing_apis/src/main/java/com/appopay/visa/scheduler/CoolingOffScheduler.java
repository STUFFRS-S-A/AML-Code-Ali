package com.appopay.visa.scheduler;

import com.appopay.visa.entity.DeviceEntity;
import com.appopay.visa.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Component
class CoolingOffScheduler {

    @Autowired
    private DeviceRepository deviceRepository;

    @Scheduled(fixedRate = 1800000) // 30 mins
    public void coolingOffScheduler() {
        Instant fourHoursAgo = Instant.now().minus(4, ChronoUnit.HOURS);
        System.out.println(fourHoursAgo);

        List<DeviceEntity> list = deviceRepository.findAllWithUpdatedTimeBeforeAndStatus(fourHoursAgo, "COOLING_OFF");

        for(DeviceEntity i : list){
            System.out.println(i.getDeviceId());
            i.setStatus("ACTIVE");
        }

        deviceRepository.saveAll(list);
    }
}