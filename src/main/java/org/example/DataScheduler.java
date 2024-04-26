package org.example;

import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.client.UrfuWebClient;
import org.example.repo.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true")
public class DataScheduler {

    public DataScheduler(@Autowired UrfuWebClient urfuClient, @Autowired DbService dbService) {
        this.urfuClient = urfuClient;
        this.dbService = dbService;
    }

    private final UrfuWebClient urfuClient;

    private final DbService dbService;

    private final static Logger LOGGER = LogManager.getLogger();

    @Scheduled(fixedDelayString = "#{new Integer(${app.scheduler.interval}) * 1000}")
    public void update() {
        LOGGER.info("is it time to update?");
        var lastUpdateTime = dbService.getMaxAvailableDate();
        if (!needToUpdate(lastUpdateTime)) {
            LOGGER.info("no");
            return;
        }
        LOGGER.info("udpating...");
        lastUpdateTime = LocalDate.now();
        var data = urfuClient.getFullGroupData(lastUpdateTime, lastUpdateTime.plusDays(7))
                .stream()
                .filter(elem -> elem.auditoryLocation() != null && elem.auditoryLocation().endsWith("Тургенева, 4"))
                .filter(elem -> elem.auditoryTitle() != null && (elem.auditoryTitle().startsWith("6") || elem.auditoryTitle().startsWith("5")))
                .toList();
        LOGGER.info("got all data from api");
        dbService.clearData();
        LOGGER.info("old data cleared");
        dbService.addAll(data);
        LOGGER.info("update was called");
    }

    private boolean needToUpdate(LocalDate lastUpdateTime) {
        var currTime = LocalDate.now();
        var updateTime = lastUpdateTime.plusDays(7);
        return !updateTime.isAfter(currTime);
    }
}
