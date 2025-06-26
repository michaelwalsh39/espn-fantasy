package com.espnfantasy.model;

import java.util.Date;

public class Standings {
    private int leagueId;
    private int teamId;
    private Integer wins;
    private Integer losses;
    private Integer ties;
    private Double pointsFor;
    private Double pointsAgainst;
    private Double playoffPct;
    private Integer projWins;
    private Integer projLosses;
    private Date runDate;

    public Standings(int leagueId, int teamId, Integer wins, Integer losses, Integer ties,
                    Double pointsFor, Double pointsAgainst, Double playoffPct,
                    Integer projWins, Integer projLosses, Date runDate) {
        this.leagueId = leagueId;
        this.teamId = teamId;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.pointsFor = pointsFor;
        this.pointsAgainst = pointsAgainst;
        this.playoffPct = playoffPct;
        this.projWins = projWins;
        this.projLosses = projLosses;
        this.runDate = runDate;
    }

    public int getLeagueId() { return leagueId; }
    public int getTeamId() { return teamId; }
    public Integer getWins() { return wins; }
    public Integer getLosses() { return losses; }
    public Integer getTies() { return ties; }
    public Double getPointsFor() { return pointsFor; }
    public Double getPointsAgainst() { return pointsAgainst; }
    public Double getPlayoffPct() { return playoffPct; }
    public Integer getProjWins() { return projWins; }
    public Integer getProjLosses() { return projLosses; }
    public Date getRunDate() { return runDate; }

    public void setPlayoffPct(Double playoffPct) {
        this.playoffPct = playoffPct;
    }

    public void setProjWins(Integer projWins) {
        this.projWins = projWins;
    }

    public void setProjLosses(Integer projLosses) {
        this.projLosses = projLosses;
    }

}
