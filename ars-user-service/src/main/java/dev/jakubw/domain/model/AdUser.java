package dev.jakubw.domain.model;

import java.util.Set;

public class AdUser {

    private String id;
    private String username;
    private String email;

    private Set<AdTag> tags;

    public AdUser(String id, String username, String email, Set<AdTag> tags) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Set<AdTag> getTags() {
        return tags;
    }
}
