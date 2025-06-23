package com.myfantasy.model;


public class Owner {
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private int teamId;

    public Owner(String id, String firstName, String lastName, String displayName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setTeamId(int teamId) { 
        this.teamId = teamId; 
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (ownerId=" + id + ", teamId=" + teamId + ")";
    }
}