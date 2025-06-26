package com.espnfantasy.model;

public class Player {
    private int id;
    private String firstName;
    private String lastName;
    private Integer defaultPositionId;
    private Double percentOwned;
    private Integer proTeamId;

    public Player(int id, String firstName, String lastName, Integer defaultPositionId, Double percentOwned, Integer proTeamId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.defaultPositionId = defaultPositionId;
        this.percentOwned = percentOwned;
        this.proTeamId = proTeamId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getDefaultPositionId() {
        return defaultPositionId;
    }

    public Double getPercentOwned() {
        return percentOwned;
    }

    public Integer getProTeamId() {
        return proTeamId;
    }
}