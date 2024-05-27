package org.example.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record DtoGroupData(
        int pairNumber,
        LocalDate date,
        String auditoryTitle,
        String auditoryLocation,
        @JsonProperty("title")
        String lesson,
        @JsonProperty("loadType")
        String lessonType,
        String teacherName
) {
}
