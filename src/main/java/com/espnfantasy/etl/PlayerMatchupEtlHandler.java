package com.espnfantasy.etl;

import java.sql.Connection;
import java.util.List;
import java.util.Iterator;

import com.espnfantasy.dao.MatchupScoringPlayerDao;
import com.espnfantasy.model.MatchupScoringPlayer;
import com.espnfantasy.store.DataStore;
import com.fasterxml.jackson.databind.JsonNode;

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
                            int lineupSlotId = entry.get("lineupSlotId").asInt();

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
                                                playerId, statId, points, scoringPeriodId, teamId, matchupId, "stat_daily", lineupSlotId
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
