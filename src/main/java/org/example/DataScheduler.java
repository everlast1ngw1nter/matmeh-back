package org.example;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.client.UrfuWebClient;
import org.example.repo.DbService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true")
public class DataScheduler {

    private final UrfuWebClient urfuClient;

    private final DbService dbService;
    private static LocalDate lastUpdateTime = LocalDate.of(1999, 12, 24);

    private final static Logger LOGGER = LogManager.getLogger();

    @Scheduled(fixedDelayString = "#{new Integer(${app.scheduler.interval}) * 1000}")
    public void update() {
        if (LocalDate.now().isEqual(lastUpdateTime)) {
            return;
        }
        lastUpdateTime = LocalDate.now();
        var data = urfuClient.getFullGroupData(lastUpdateTime)
                .stream()
                .filter(elem -> elem.auditoryLocation() != null && elem.auditoryLocation().endsWith("Тургенева, 4"))
                .filter(elem -> elem.auditoryTitle() != null && (elem.auditoryTitle().startsWith("6") || elem.auditoryTitle().startsWith("5")))
                .toList();
        dbService.clearData();
        dbService.addAll(data);
        LOGGER.info("update was called");
    }
}
