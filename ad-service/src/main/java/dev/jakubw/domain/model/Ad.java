package dev.jakubw.domain.model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Ad {

    private String id;
    private String name;
    private String adUrl;
    private HashMap<LocalDate,AdDailyImpression> impressions;
    private LocalDate campaignEnd;
    private Long maxDayCount;
    private AdStatus status;

    public Ad(String id, String name, String adUrl,List<AdDailyImpression> impressions,LocalDate campaignEnd, Long maxDayCount, AdStatus status){
        this.id = id;
        this.name = name;
        this.adUrl = adUrl;
        this.impressions = (impressions != null) ? impressions.stream()
                .collect(Collectors.toMap(
                        AdDailyImpression::getDay,
                        imp -> imp,
                        (existing, replacement) -> existing,
                        HashMap::new
                )) : new HashMap<>();
        this.status = status;
        this.campaignEnd = campaignEnd;
        this.maxDayCount = maxDayCount;
    }

    public Ad(String id, String name, String adUrl, LocalDate campaignEnd, Long maxDayCount, AdStatus status){
        this.id = id;
        this.name = name;
        this.adUrl = adUrl;
        this.impressions = new HashMap<>();
        this.status = status;
        this.campaignEnd = campaignEnd;
        this.maxDayCount = maxDayCount;
    }
    public void setStatus(AdStatus status) {this.status = status;}
    public Long getMaxDayCount() {return maxDayCount;}
    public LocalDate getCampaignEnd() {return campaignEnd;}
    public String getId() {return id;}
    public String getName() {return name;}
    public String getAdUrl() {return adUrl;}
    public HashMap<LocalDate,AdDailyImpression> getImpressions() {return impressions;}
    public AdStatus getStatus() {return status;}
}
