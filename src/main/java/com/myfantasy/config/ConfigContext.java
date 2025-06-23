package com.myfantasy.config;

public class ConfigContext {

    private final int leagueId;
    private final String sport;
    private final String season;
    private final String espnLeagueId;

    public ConfigContext(int leagueId, String sport, String season, String espnLeagueId) {
        this.leagueId = leagueId;
        this.sport = sport;
        this.season = season;
        this.espnLeagueId = espnLeagueId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public String getSport() {
        return sport;
    }

    public String getSeason() {
        return season;
    }

    public String getEspnLeagueId() {
        return espnLeagueId;
    }
}
