package com.espnfantasy.model;

public class MatchupScoringPlayer {
    private int playerId;
    private Integer statId;
    private double points;
    private int scoringPeriodId;
    private int teamId;
    private int matchupId;
    private String aggType;
    private Integer lineupSlotId;

    public MatchupScoringPlayer(int playerId, int statId, double points, int scoringPeriodId, Integer teamId, int matchupId, String aggType, Integer lineupSlotId) {
        this.playerId = playerId;
        this.statId = statId;
        this.points = points;
        this.scoringPeriodId = scoringPeriodId;
        this.teamId = teamId;
        this.matchupId = matchupId;
        this.aggType = aggType;
        this.lineupSlotId = lineupSlotId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Integer getStatId() {
        return statId;
    }

    public void setStatId(Integer statId) {
        this.statId = statId;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getScoringPeriodId() {
        return scoringPeriodId;
    }

    public void setScoringPeriodId(int scoringPeriodId) {
        this.scoringPeriodId = scoringPeriodId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getMatchupId() {
        return matchupId;
    }

    public void setMatchupId(int matchupId) {
        this.matchupId = matchupId;
    }

     public String getAggType() { 
        return aggType;
    }

    public void setAggType(int matchupId) {
        this.matchupId = matchupId;
    }

    public Integer getLineupSlotId() {
        return lineupSlotId;
    }

    public void setLineupSlotId(Integer lineupSlotId) {
        this.lineupSlotId = lineupSlotId;
    }

}
