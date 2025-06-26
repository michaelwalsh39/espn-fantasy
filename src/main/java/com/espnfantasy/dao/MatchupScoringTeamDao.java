package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.espnfantasy.model.MatchupScoringTeam;

public class MatchupScoringTeamDao {

    public void upsertDailyStats(Connection conn, List<MatchupScoringTeam> scoring, int leagueId) throws SQLException {
        String deleteSQL = "DELETE FROM matchup_scoring_period_team WHERE matchup_id = ? AND team_id = ? AND league_id = ? AND agg_type = ?";
        String insertSQL = "INSERT INTO matchup_scoring_period_team (league_id, matchup_id, team_id, scoring_period_id, stat_id, agg_type, points) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            for (MatchupScoringTeam score : scoring) {
                deleteStmt.setInt(1, score.getMatchupId());
                deleteStmt.setInt(2, score.getTeamId());
                deleteStmt.setInt(3, leagueId);
                deleteStmt.setString(4, score.getAggType());
                deleteStmt.executeUpdate();

                insertStmt.setInt(1, leagueId);
                insertStmt.setInt(2, score.getMatchupId());
                insertStmt.setInt(3, score.getTeamId());
                insertStmt.setInt(4, score.getScoringPeriodId());
                if (score.getStatId() != null) {
                    insertStmt.setInt(5, score.getStatId());
                } else {
                    insertStmt.setNull(5, java.sql.Types.INTEGER);
                }
                insertStmt.setString(6, score.getAggType());
                insertStmt.setDouble(7, score.getPoints());
                insertStmt.addBatch();
            }

            insertStmt.executeBatch();
        }
    }

    public void upsertAggregatedStats(Connection conn, String inputAggType, String outputAggType, int leagueId) throws SQLException {
        String deleteSql = "DELETE FROM matchup_scoring_period_team WHERE agg_type = ? AND league_id = ?";
        
        String insertSql =
            "INSERT INTO matchup_scoring_period_team (league_id, matchup_id, team_id, points, agg_type) " +
            "SELECT " +
            "  league_id, " +
            "  matchup_id, " +
            "  team_id, " +
            "  SUM(points), " +
            "  ? AS agg_type " +
            "FROM matchup_scoring_period_team " +
            "WHERE agg_type = ? AND league_id = ? " +
            "GROUP BY league_id, matchup_id, team_id";

        try (
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql)
        ) {
            // DELETE old weekly entries
            deleteStmt.setString(1, outputAggType);
            deleteStmt.setInt(2, leagueId);
            deleteStmt.executeUpdate();

            // INSERT new weekly entries
            insertStmt.setString(1, outputAggType);
            insertStmt.setString(2, inputAggType);
            insertStmt.setInt(3, leagueId);
            insertStmt.executeUpdate();
        }
    }

}
