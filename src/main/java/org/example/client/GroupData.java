package org.example.client;

import java.time.LocalDate;

public record GroupData(
        int pairNumber,
        LocalDate date,
        String auditoryTitle,
        String auditoryLocation
) {
}
