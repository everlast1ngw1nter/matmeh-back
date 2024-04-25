package org.example;

import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repo.DbService;
import org.example.repo.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://localhost:5000", "http://localhost:63342"})
public class AppController {

    public AppController(@Autowired DbService dbService) {
        this.dbService = dbService;
    }

    private final DbService dbService;

    private static final Logger LOGGER = LogManager.getLogger();

    @GetMapping("/date/{time}")
    List<ScheduleDto> registerChat(@PathVariable LocalDate time) {
        LOGGER.info("get pairs by date = " + time);
        return dbService.getAllByTime(time);
    }

    @GetMapping("/date/{time}/pair/{pairNumber}")
    List<ScheduleDto> deleteChat(@PathVariable LocalDate time, @PathVariable int pairNumber) {
        LOGGER.info("get pairs by pairNumber = " + pairNumber +
                " and date " + time);
        return dbService.getAllByPairAndTime(pairNumber, time);

    }
}
