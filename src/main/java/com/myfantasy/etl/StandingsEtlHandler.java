package com.myfantasy.etl;

import com.fasterxml.jackson.databind.JsonNode;
import com.myfantasy.model.Standings;
import com.myfantasy.store.DataStore;
import com.myfantasy.dao.StandingsDao;

import java.sql.Connection;


public class StandingsEtlHandler implements EtlHandler {
    private final int leagueId;

    public StandingsEtlHandler(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public void handle(JsonNode root, DataStore dataStore, Connection conn) throws Exception {

        if (root.has("teams")) {
            for (JsonNode teamNode : root.get("teams")) {
                int teamId = teamNode.path("id").asInt();
                Double playoffPct = teamNode.path("currentSimulationResults").path("playoffPct").asDouble();
                int projWins = teamNode.path("currentSimulationResults").path("modeRecord").path("wins").asInt();
                int projLosses = teamNode.path("currentSimulationResults").path("modeRecord").path("losses").asInt();

                // Update the corresponding Standings object in the DataStore
                for (Standings s : dataStore.getStandings()) {
                    if (s.getTeamId() == teamId && s.getLeagueId() == leagueId) {
                        s.setPlayoffPct(playoffPct);
                        s.setProjWins(projWins);
                        s.setProjLosses(projLosses);
                        break;
                    }
                }
            }
        }
        StandingsDao standingsDao = new StandingsDao();
        standingsDao.upsert(conn, dataStore.getStandings(), leagueId);

    }
}
