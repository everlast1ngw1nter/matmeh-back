package org.example.repo;

import java.time.LocalDate;
import java.util.List;
import org.example.client.GroupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbService {

    public DbService(@Autowired Dao dao) {
        this.dao = dao;
    }

    private final Dao dao;

    public void clearData() {
        dao.deleteAll();
    }

    public void addAll(List<GroupData> groupDataList) {
        for (var elem : groupDataList) {
            dao.add(elem);
        }
    }

    public List<ScheduleDto> getAll() {
        return dao.getAll();
    }

    public List<ScheduleDto> getAllByPairAndTime(int pair, LocalDate date) {
        return getAllByTime(date)
                .stream()
                .filter(elem -> elem.pairNumber() == pair)
                .toList();
    }

    public List<ScheduleDto> getAllByTime(LocalDate date) {
        return dao.getAll()
                .stream()
                .filter(elem -> elem.date().isEqual(date))
                .toList();
    }

    public LocalDate getMaxAvailableDate() {
        return dao.getMaxAvailableDate();
    }
}
