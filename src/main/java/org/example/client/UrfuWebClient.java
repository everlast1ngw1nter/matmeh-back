package org.example.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UrfuWebClient {
    public UrfuWebClient(@Autowired WebClient urfuClient) {
        this.urfuClient = urfuClient;
    }

    private final WebClient urfuClient;

    private final static int FIRST_COURSE = 1;

    private final static int LAST_COURSE = 6;

    public List<GroupData> getFullGroupData(LocalDate startDate, LocalDate endDate) {
        var resultData = new ArrayList<GroupData>();
        for (var division : filterUselessDivisionData(getDivisionsList())){
            for (var course = FIRST_COURSE; course <= LAST_COURSE; course++) {
                var courseData = getCourseInfo(division.id(), course);
                for (var group : courseData) {
                    resultData.addAll(getGroupDataList(group.id(), startDate, endDate).events());
                }
            }
        }
        return resultData;
    }

    public List<DivisionData> getDivisionsList() {
        return urfuClient
                .get()
                .uri("divisions")
                .retrieve()
                .bodyToFlux(DivisionData.class)
                .collectList()
                .block();
    }

    public List<CourseData> getCourseInfo(int division, int course) {
        return urfuClient
                .get()
                .uri("divisions/{division}/groups?course={course}", division, course)
                .retrieve()
                .bodyToFlux(CourseData.class)
                .collectList()
                .block();
    }

    public ListGroupData getGroupDataList(int group, LocalDate startDate, LocalDate endDate) {
        return urfuClient
                .mutate()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build()
                .get()
                .uri("groups/{group}/schedule?date_gte={startDate}&date_lte={endDate}", group, startDate, endDate)
                .retrieve()
                .bodyToMono(ListGroupData.class)
                .block();
    }

    private List<DivisionData> filterUselessDivisionData(List<DivisionData> data) {
        var parents = data
                .stream()
                .map(DivisionData::parentId)
                .collect(Collectors.toUnmodifiableSet());
        return data
                .stream()
                .filter(elem -> !parents.contains(elem.id()))
                .toList();
    }
}
