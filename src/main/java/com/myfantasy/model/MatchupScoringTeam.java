package com.myfantasy.model;

public class MatchupScoringTeam {
    private int matchupId;
    private int teamId;
    private int scoringPeriodId;
    private Integer statId;
    private String aggType;
    private double points;

    public MatchupScoringTeam(int matchupId, int teamId, int scoringPeriodId, Integer statId, String aggType, double points) {
        this.matchupId = matchupId;
        this.teamId = teamId;
        this.scoringPeriodId = scoringPeriodId;
        this.statId = statId;
        this.aggType = aggType;
        this.points = points;
    }

    public void setMatchupId(int matchupId) {
        this.matchupId = matchupId;
    }

    public void setScoringPeriodId(int scoringPeriodId) {
        this.scoringPeriodId = scoringPeriodId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
    }

    public void setAggType(String aggType) {
        this.aggType = aggType;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getMatchupId() { return matchupId; }
    public int getTeamId() { return teamId; }
    public int getScoringPeriodId() { return scoringPeriodId; }
    public Integer getStatId() { return statId; }
    public String getAggType() { return aggType; }
    public double getPoints() { return points; }

}