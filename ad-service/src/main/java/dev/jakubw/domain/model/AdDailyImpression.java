package dev.jakubw.domain.model;

import java.time.LocalDate;

public class AdDailyImpression {

    private String id;

    private LocalDate day;

    private Long count;

    public AdDailyImpression(String id, LocalDate day, Long count) {
        this.id = id;
        this.day = day;
        this.count = count;
    }

    public void incrementCount(Long extraImpressions){
        count += extraImpressions;
    }

    public String getId() {return id;}
    public LocalDate getDay() {return day;}
    public Long getCount() {return count;}
}
