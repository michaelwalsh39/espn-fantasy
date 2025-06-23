package com.myfantasy.etl;

import java.sql.Connection;
import java.util.List;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.myfantasy.model.MatchupScoringPlayer;
import com.myfantasy.store.DataStore;
import com.myfantasy.dao.MatchupScoringPlayerDao;

public class PlayerMatchupEtlHandler implements EtlHandler {
    private final int leagueId;

    public PlayerMatchupEtlHandler(int leagueId) {
        this.leagueId = leagueId;
    }    

    @Override
    public void handle(JsonNode root, DataStore dataStore, Connection conn) throws Exception {
        JsonNode schedule = root.get("schedule");
        if (schedule != null && schedule.isArray()) {
            for (JsonNode matchup : schedule) {
                int matchupId = matchup.get("id").asInt();

                for (String side : List.of("home", "away")) {
                    JsonNode teamNode = matchup.get(side);
                    if (teamNode == null || teamNode.isNull()) continue;

                    int teamId = teamNode.get("teamId").asInt();

                    JsonNode roster = teamNode.get("rosterForCurrentScoringPeriod");
                    if (roster != null && roster.has("entries")) {
                        for (JsonNode entry : roster.get("entries")) {
                            int playerId = entry.get("playerId").asInt();

                            JsonNode statsArray = entry
                                .path("playerPoolEntry")
                                .path("player")
                                .path("stats");

                            if (statsArray != null && statsArray.isArray()) {
                                for (JsonNode statEntry : statsArray) {
                                    int scoringPeriodId = statEntry.path("scoringPeriodId").asInt();
                                    JsonNode appliedStats = statEntry.path("appliedStats");

                                    if (appliedStats != null && appliedStats.isObject()) {
                                        Iterator<String> fieldNames = appliedStats.fieldNames();
                                        while (fieldNames.hasNext()) {
                                            String statIdStr = fieldNames.next();
                                            int statId = Integer.parseInt(statIdStr);
                                            double points = appliedStats.get(statIdStr).asDouble();

                                            MatchupScoringPlayer msp = new MatchupScoringPlayer(
                                                playerId, statId, points, scoringPeriodId, teamId, matchupId, "stat_daily"
                                            );
                                            dataStore.addMatchupScoringPlayer(msp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // upsert daily stats, then weekly
        MatchupScoringPlayerDao scoringPlayerDao = new MatchupScoringPlayerDao();
        scoringPlayerDao.upsertDailyStats(conn, dataStore.getMatchupScoringPlayers(), leagueId);
    }

}
