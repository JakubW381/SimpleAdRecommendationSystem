package dev.jakubw.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdProvider {

    private String id;
    private String name;
    private List<Ad> ads;

    private LocalDateTime creationDate;

    public AdProvider(String id, String name, List<Ad> ads, LocalDateTime creationDate){
        this.id = id;
        this.name = name;
        this.ads = ads != null ? new ArrayList<>(ads) : new ArrayList<>();
        this.creationDate = creationDate;
    }
    public AdProvider(String id, String name, LocalDateTime creationDate){
        this.id = id;
        this.name = name;
        this.ads = new ArrayList<>();
        this.creationDate = creationDate;
    }
    public void addAd(Ad ad){
        ads.add(ad);
    }

    public LocalDateTime getCreationDate() {return creationDate;}
    public String getId() {return id;}
    public String getName() {return name;}
    public List<Ad> getAds() {return ads;}
}
