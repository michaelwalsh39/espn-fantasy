package com.myfantasy.model;

public class Matchup {
    private int matchupId;
    private int matchupPeriodId;
    private int homeTeamId;
    private int awayTeamId;

    public Matchup(int matchupId, int matchupPeriodId, int homeTeamId, int awayTeamId) {
        this.matchupId = matchupId;
        this.matchupPeriodId = matchupPeriodId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
    }

    public int getMatchupId() { return matchupId; }
    public int getMatchupPeriodId() { return matchupPeriodId; }
    public int getHomeTeamId() { return homeTeamId; }
    public int getAwayTeamId() { return awayTeamId; }
}
