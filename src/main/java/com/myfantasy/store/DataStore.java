package com.myfantasy.store;

import com.myfantasy.etl.EtlHandler;
import com.myfantasy.model.Draft;
import com.myfantasy.model.Matchup;
import com.myfantasy.model.Owner;
import com.myfantasy.model.Team;
import com.myfantasy.model.ScoringSetting;
import com.myfantasy.model.MatchupScoringTeam;
import com.myfantasy.model.MatchupScoringPlayer;
import com.myfantasy.model.MatchupPeriodCombo;
import com.myfantasy.model.Player;
import com.myfantasy.model.Standings;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private final List<Team> teams = new ArrayList<>();
    private final List<Owner> owners = new ArrayList<>();
    private final List<ScoringSetting> scoringSettings = new ArrayList<>();
    private final List<Matchup> matchups = new ArrayList<>();
    private final List<MatchupScoringTeam> matchupScoringTeams = new ArrayList<>();
    private final List<MatchupPeriodCombo> matchupPeriodCombos = new ArrayList<>();
    private final List<MatchupScoringPlayer> matchupScoringPlayers = new ArrayList<>();
    private final List<EtlRegistryEntry> etlRegistry = new ArrayList<>();
    private final List<Player> playerList = new ArrayList<>();
    private final List<Standings> standings = new ArrayList<>();
    private final List<Draft> drafts = new ArrayList<>();

    public void addMatchupScoringPlayer(MatchupScoringPlayer msp) {
        matchupScoringPlayers.add(msp);
    }

    public void addMatchupPeriodCombo(MatchupPeriodCombo combo) {
        matchupPeriodCombos.add(combo);
    }

    public void addScoringRule(ScoringSetting setting) {
        scoringSettings.add(setting);
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    public void addMatchup(Matchup m) {
        matchups.add(m);
    }

    public void addMatchupScoringTeam(MatchupScoringTeam p) {
        matchupScoringTeams.add(p);
    }

    public void addEtlRegistryEntry(String view, String filter, EtlHandler etlHandler) {
        etlRegistry.add(new EtlRegistryEntry(view, filter, etlHandler));
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void addStandings(Standings standing) {
        standings.add(standing);
    }

    public List<Draft> getDrafts() {
        return drafts;
    }

    public List<MatchupScoringPlayer> getMatchupScoringPlayers() {
        return matchupScoringPlayers;
    }

    public List<MatchupPeriodCombo> getMatchupPeriodCombos() {
        return matchupPeriodCombos;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public List<ScoringSetting> getScoringRules() {
        return scoringSettings;
    }

    public List<Matchup> getMatchups() {
        return matchups;
    }

    public List<MatchupScoringTeam> getMatchupScoringTeams() {
        return matchupScoringTeams;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Standings> getStandings() {
        return standings;
    }

    public List<EtlRegistryEntry> getEtlRegistry() {
        return etlRegistry;
    }

    public static class EtlRegistryEntry {
        private final String view;
        private final String filter;
        private final EtlHandler etlHandler;

        public EtlRegistryEntry(String view, String filter, EtlHandler etlHandler) {
            this.view = view;
            this.filter = filter;
            this.etlHandler = etlHandler;
        }

        public String getView() {
            return view;
        }

        public String getFilter() {
            return filter;
        }

        public EtlHandler getEtlHandler() {
            return etlHandler;
        }
    }

}
