package org.example.repo;

import java.time.LocalDate;

public record ScheduleDto(
        int pairNumber,
        String auditory,
        LocalDate date,
        String lesson,
        String lessonType,
        String groupName,
        String teacherName
) {
}
