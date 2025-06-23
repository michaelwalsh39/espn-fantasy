package com.myfantasy.model;

import java.time.LocalDate;

public class Draft {
    private int leagueId;
    private int season;
    private int teamId;
    private int overallPickNumber;
    private int round;
    private int roundPickNumber;
    private boolean isKeeper;
    private boolean isReservedForKeeper;
    private int playerId;
    private Integer autodraftTypeId;
    private Integer nominatingTeamId;
    private Double bidAmount;
    private LocalDate completedDate;

    public int getLeagueId() { return leagueId; }
    public void setLeagueId(int leagueId) { this.leagueId = leagueId; }

    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }

    public int getTeamId() { return teamId; }
    public void setTeamId(int teamId) { this.teamId = teamId; }

    public int getOverallPickNumber() { return overallPickNumber; }
    public void setOverallPickNumber(int overallPickNumber) { this.overallPickNumber = overallPickNumber; }

    public int getRound() { return round; }
    public void setRound(int round) { this.round = round; }

    public int getRoundPickNumber() { return roundPickNumber; }
    public void setRoundPickNumber(int roundPickNumber) { this.roundPickNumber = roundPickNumber; }

    public boolean isKeeper() { return isKeeper; }
    public void setKeeper(boolean keeper) { isKeeper = keeper; }

    public boolean isReservedForKeeper() { return isReservedForKeeper; }
    public void setReservedForKeeper(boolean reservedForKeeper) { isReservedForKeeper = reservedForKeeper; }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public Integer getAutodraftTypeId() { return autodraftTypeId; }
    public void setAutodraftTypeId(Integer autodraftTypeId) { this.autodraftTypeId = autodraftTypeId; }

    public Integer getNominatingTeamId() { return nominatingTeamId; }
    public void setNominatingTeamId(Integer nominatingTeamId) { this.nominatingTeamId = nominatingTeamId; }

    public Double getBidAmount() { return bidAmount; }
    public void setBidAmount(Double bidAmount) { this.bidAmount = bidAmount; }

    public LocalDate getCompletedDate() { return completedDate; }
    public void setCompletedDate(LocalDate completedDate) { this.completedDate = completedDate; }
}
