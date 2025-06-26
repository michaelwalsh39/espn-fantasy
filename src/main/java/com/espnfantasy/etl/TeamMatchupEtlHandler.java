package com.espnfantasy.etl;

import com.espnfantasy.dao.ConfigDao;
import com.espnfantasy.dao.MatchupDao;
import com.espnfantasy.dao.MatchupScoringTeamDao;
import com.espnfantasy.model.Matchup;
import com.espnfantasy.model.MatchupPeriodCombo;
import com.espnfantasy.model.MatchupScoringTeam;
import com.espnfantasy.store.DataStore;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TeamMatchupEtlHandler implements EtlHandler {
    private final int leagueId;

    public TeamMatchupEtlHandler(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public void handle(JsonNode root, DataStore dataStore, Connection conn) throws SQLException {
       if (root.has("schedule")) {
        for (JsonNode matchupNode : root.get("schedule")) {
            int matchupId = matchupNode.path("id").asInt();
            int matchupPeriodId = matchupNode.path("matchupPeriodId").asInt();

            JsonNode home = matchupNode.get("home");
            JsonNode away = matchupNode.get("away");

            int homeTeamId = home.path("teamId").asInt();
            int awayTeamId = away.path("teamId").asInt();

            dataStore.addMatchup(new Matchup(matchupId, matchupPeriodId, homeTeamId, awayTeamId));

            for (JsonNode teamNode : new JsonNode[]{home, away}) {
                int teamId = teamNode.path("teamId").asInt();

                // Daily total points
                JsonNode pointsByPeriod = teamNode.get("pointsByScoringPeriod");
                if (pointsByPeriod != null && pointsByPeriod.isObject()) {
                    pointsByPeriod.fields().forEachRemaining(entry -> {
                        int periodId = Integer.parseInt(entry.getKey());
                        double points = entry.getValue().asDouble();
                        dataStore.addMatchupScoringTeam(new MatchupScoringTeam(
                            matchupId, teamId, periodId, null, "daily", points));
                    });
                }

                // to process stat-specific weekly points
                // commented out for now, not sure if needed

                // JsonNode cumulativeScore = teamNode.get("cumulativeScore");
                // if (cumulativeScore != null && cumulativeScore.has("scoreByStat")) {
                //     JsonNode scoreByStat = cumulativeScore.get("scoreByStat");
                //     if (scoreByStat != null && scoreByStat.isObject()) {
                //         scoreByStat.fields().forEachRemaining(entry -> {
                //             int statId = Integer.parseInt(entry.getKey());
                //             JsonNode statDetails = entry.getValue();
                //             double points = statDetails.path("score").asDouble(0.0);
                //             // System.out.println("statId: " + statId + ", points: " + points);
                //             dataStore.addMatchupScoringTeam(new MatchupScoringTeam(
                //                     matchupId, teamId, matchupPeriodId, statId, "stat_weekly", points));
                //         });
                //     }
                // }
            }
        }
    }

    // insert matchups
    new MatchupDao().upsert(conn, dataStore.getMatchups(), leagueId);

    // insert daily stats, then weekly using daily
    MatchupScoringTeamDao scoringTeamDao = new MatchupScoringTeamDao();
    scoringTeamDao.upsertDailyStats(conn, dataStore.getMatchupScoringTeams(), leagueId);


    // save off matchupId / periodId combinations to use for player daily stats
    // but only do it for recent matchups
    ConfigDao configDao = new ConfigDao();
    int startMatchupPeriodId = configDao.loadRecentMatchupPeriodId(leagueId, conn);

    Set<MatchupPeriodCombo> uniqueCombos = new HashSet<>();
    for (Matchup matchup : dataStore.getMatchups()) {
        int matchupPeriodId = matchup.getMatchupPeriodId();

        if (matchupPeriodId >= startMatchupPeriodId) {
            for (MatchupScoringTeam teamScore : dataStore.getMatchupScoringTeams()) {
                if (teamScore.getMatchupId() == matchup.getMatchupId()) {
                    int scoringPeriodId = teamScore.getScoringPeriodId();
                    // System.out.println("matchupPeriodId: " + matchupPeriodId + ", scoringPeriodId: " + scoringPeriodId);
                    uniqueCombos.add(new MatchupPeriodCombo(matchupPeriodId, scoringPeriodId));
                }
            }
        }
    }
    for (MatchupPeriodCombo combo : uniqueCombos) {
        // save off in datastore
        String view = "mBoxscore&scoringPeriodId=" + combo.getScoringPeriodId() +  "&matchupPeriodId=" + combo.getMatchupPeriodId();
        dataStore.addEtlRegistryEntry(
            view,
            "{}",
            new PlayerMatchupEtlHandler(leagueId)
        );
    }
}
}