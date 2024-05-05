package org.example.repo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.client.GroupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class Dao {

    public Dao(@Autowired JdbcTemplate jdbcTemplateMap) {
        this.jdbcTemplateMap = jdbcTemplateMap;
    }

    private final JdbcTemplate jdbcTemplateMap;

    public void add(GroupData groupData) {
        try {
            jdbcTemplateMap.update(
                    "INSERT INTO schedule (pair_number, auditory, date) VALUES (?, ?, ?)",
                    groupData.pairNumber(), groupData.auditoryTitle(), groupData.date());
        } catch (DuplicateKeyException ignored) {

        }
    }

    public List<ScheduleDto> getAll() {
        var rowSet = jdbcTemplateMap.queryForRowSet("SELECT * FROM schedule");
        return convertToDto(rowSet);
    }

    public void deleteAll() {
        jdbcTemplateMap.update("DELETE FROM schedule");
    }

    public LocalDate getMinAvailableDate() {
        var rs = jdbcTemplateMap.queryForRowSet("SELECT MIN(date) FROM schedule");
        rs.next();
        try {
            return rs.getTimestamp(1).toLocalDateTime().toLocalDate();
        } catch (NullPointerException e) {
            return LocalDate.of(1999, 12, 24);
        }
    }

    private List<ScheduleDto> convertToDto(SqlRowSet rowSet) {
        var listDto = new ArrayList<ScheduleDto>();
        while (rowSet.next()) {
            listDto.add(new ScheduleDto(
                    rowSet.getInt("pair_number"),
                    rowSet.getString("auditory"),
                    rowSet.getDate("date").toLocalDate()
            ));
        }
        return listDto;
    }
}
