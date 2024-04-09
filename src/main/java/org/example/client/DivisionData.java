package org.example.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DivisionData(
        int id,
        int parentId,
        @JsonProperty("title")
        String divisionName
) {
}
