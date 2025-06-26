package com.espnfantasy.model;

public class Team {
    private int id;
    private String name;
    private String abbrev;
    private String primaryOwner;

    public Team(int id, String name, String abbrev, String primaryOwner) {
        this.id = id;
        this.name = name;
        this.abbrev = abbrev;
        this.primaryOwner = primaryOwner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    @Override
    public String toString() {
        return name + " (" + abbrev + ")";
    }
}
