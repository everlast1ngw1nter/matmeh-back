package org.example.client;

import java.util.List;

public record ListGroupData(
        DtoGroup group,
        List<DtoGroupData> events) {
}
