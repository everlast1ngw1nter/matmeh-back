package org.example.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CourseData(
        int id,
        int divisionId,
        int course,
        @JsonProperty("title")
        String groupName
) {
}
