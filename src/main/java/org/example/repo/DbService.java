package org.example.repo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.client.GroupData;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbService {

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

    public List<ScheduleDto> getAllByPair(int pair) {
        return dao.getAll()
                .stream()
                .filter(elem -> elem.pairNumber() == pair)
                .toList();
    }
}
