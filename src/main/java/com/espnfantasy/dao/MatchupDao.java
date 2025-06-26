package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.espnfantasy.model.Matchup;

public class MatchupDao {

    public void upsert(Connection conn, List<Matchup> matchups, int leagueId) throws SQLException {
        String deleteSQL = "DELETE FROM matchup WHERE matchup_id = ? AND league_id = ?";
        String insertSQL = "INSERT INTO matchup (league_id, matchup_id, matchup_period_id, home_team_id, away_team_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            for (Matchup matchup : matchups) {
                deleteStmt.setInt(1, matchup.getMatchupId());
                deleteStmt.setInt(2, leagueId);
                deleteStmt.executeUpdate();

                insertStmt.setInt(1, leagueId);
                insertStmt.setInt(2, matchup.getMatchupId());
                insertStmt.setInt(3, matchup.getMatchupPeriodId());
                insertStmt.setInt(4, matchup.getHomeTeamId());
                insertStmt.setInt(5, matchup.getAwayTeamId());
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }
    }
}