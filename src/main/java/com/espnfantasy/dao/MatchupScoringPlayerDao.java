package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.espnfantasy.model.MatchupScoringPlayer;

public class MatchupScoringPlayerDao {

    public void upsertDailyStats(Connection conn, List<MatchupScoringPlayer> scoring, int leagueId) throws SQLException {
        String deleteSQL = "DELETE FROM matchup_scoring_period_player WHERE matchup_id = ? AND scoring_period_id = ? AND league_id = ? AND agg_type = ?";
        String insertSQL = "INSERT INTO matchup_scoring_period_player (league_id, matchup_id, team_id, player_id, scoring_period_id, stat_id, agg_type, points, lineup_slot_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            // Step 1: unique delete keys
            Set<String> deleteKeys = new HashSet<>();
            for (MatchupScoringPlayer score : scoring) {
                String key = score.getMatchupId() + "|" + score.getScoringPeriodId() + "|" + score.getAggType();
                deleteKeys.add(key);
            }

            // Step 2: batch deletes
            for (String key : deleteKeys) {
                String[] parts = key.split("\\|");
                int matchupId = Integer.parseInt(parts[0]);
                int scoringPeriodId = Integer.parseInt(parts[1]);
                String aggType = parts[2];

                deleteStmt.setInt(1, matchupId);
                deleteStmt.setInt(2, scoringPeriodId);
                deleteStmt.setInt(3, leagueId);
                deleteStmt.setString(4, aggType);
                deleteStmt.addBatch();
            }
            deleteStmt.executeBatch();

            // Step 3: batch inserts
            for (MatchupScoringPlayer score : scoring) {
                insertStmt.setInt(1, leagueId);
                insertStmt.setInt(2, score.getMatchupId());
                insertStmt.setInt(3, score.getTeamId());
                insertStmt.setInt(4, score.getPlayerId());
                insertStmt.setInt(5, score.getScoringPeriodId());
                if (score.getStatId() != null) {
                    insertStmt.setInt(6, score.getStatId());
                } else {
                    insertStmt.setNull(6, java.sql.Types.INTEGER);
                }
                insertStmt.setString(7, score.getAggType());
                insertStmt.setDouble(8, score.getPoints());
                insertStmt.setInt(9, score.getLineupSlotId());
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }
    }
    public void upsertAggregatedStats(Connection conn, String inputAggType, String outputAggType, int leagueId) throws SQLException {
        String deleteSql = "DELETE FROM matchup_scoring_period_player WHERE agg_type = ? AND league_id = ?";
        String columnList = "";
        String insertSql;

        if (outputAggType.equals("stat_weekly")) {
            columnList = "league_id, matchup_id, team_id, player_id, stat_id";
        }
        else if (outputAggType.equals("daily")) {
            columnList = "league_id, matchup_id, scoring_period_id, team_id, player_id, lineup_slot_id";
        }
        else if (outputAggType.equals("weekly")) {
            columnList = "league_id, matchup_id, team_id, player_id";
        }

        insertSql =
            "INSERT INTO matchup_scoring_period_player (" + columnList + ", points, agg_type) " +
            "SELECT " +
            columnList + ", " +
            "  SUM(points), " +
            "  ? AS agg_type " +
            "FROM matchup_scoring_period_player " +
            "WHERE agg_type = ? AND league_id = ? " +
            "GROUP BY " + columnList;

        try (
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql)
        ) {
            deleteStmt.setString(1, outputAggType);
            deleteStmt.setInt(2, leagueId);
            deleteStmt.executeUpdate();

            insertStmt.setString(1, outputAggType);
            insertStmt.setString(2, inputAggType);
            insertStmt.setInt(3, leagueId);
            insertStmt.executeUpdate();
        }
    }

}
