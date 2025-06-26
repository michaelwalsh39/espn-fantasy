package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.espnfantasy.model.Player;

public class PlayerDao {

    public void upsert(Connection conn, List<Player> players, int leagueId) throws SQLException {
        String deleteSQL = "DELETE FROM player WHERE league_id = ?";
        
        String insertSQL = 
            "INSERT INTO player " +
            "(league_id, player_id, first_name, last_name, position_id_default, percent_owned, pro_team_id, added_at, modified_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";

        // Delete existing players for this league
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
            deleteStmt.setInt(1, leagueId);
            deleteStmt.executeUpdate();
        }

        // Insert new players
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
            for (Player p : players) {
                insertStmt.setInt(1, leagueId);
                insertStmt.setInt(2, p.getId());
                insertStmt.setString(3, p.getFirstName());
                insertStmt.setString(4, p.getLastName());

                if (p.getDefaultPositionId() != null) {
                    insertStmt.setInt(5, p.getDefaultPositionId());
                } else {
                    insertStmt.setNull(5, java.sql.Types.INTEGER);
                }

                if (p.getPercentOwned() != null) {
                    insertStmt.setDouble(6, p.getPercentOwned());
                } else {
                    insertStmt.setNull(6, java.sql.Types.NUMERIC);
                }

                if (p.getProTeamId() != null) {
                    insertStmt.setInt(7, p.getProTeamId());
                } else {
                    insertStmt.setNull(7, java.sql.Types.INTEGER);
                }

                insertStmt.addBatch();
            }

            insertStmt.executeBatch();
        }
    }
}
