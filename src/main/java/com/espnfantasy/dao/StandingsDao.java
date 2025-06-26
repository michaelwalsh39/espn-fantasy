package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.espnfantasy.model.Standings;

public class StandingsDao {

    private static final String DELETE_SQL =
        "DELETE FROM standings WHERE league_id = ? AND run_date = ?";

    private static final String INSERT_SQL =
        "INSERT INTO standings (league_id, team_id, wins, losses, ties, points_for, points_against, " +
        "playoff_pct, proj_wins, proj_losses, run_date, added_at, modified_at) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";

    public void upsert(Connection conn, List<Standings> standingsList, int leagueId) throws SQLException {
        LocalDate today = LocalDate.now();
        Date sqlRunDate = Date.valueOf(today);

        try (
            PreparedStatement deleteStmt = conn.prepareStatement(DELETE_SQL);
            PreparedStatement insertStmt = conn.prepareStatement(INSERT_SQL)
        ) {
            // Delete existing rows for this league and runDate
            deleteStmt.setInt(1, leagueId);
            deleteStmt.setDate(2, sqlRunDate);
            deleteStmt.executeUpdate();

            // Insert new rows
            for (Standings s : standingsList) {
                insertStmt.setInt(1, s.getLeagueId());
                insertStmt.setInt(2, s.getTeamId());
                insertStmt.setInt(3, s.getWins());
                insertStmt.setInt(4, s.getLosses());
                insertStmt.setInt(5, s.getTies());
                insertStmt.setDouble(6, s.getPointsFor());
                insertStmt.setDouble(7, s.getPointsAgainst());
                insertStmt.setDouble(8, s.getPlayoffPct());
                insertStmt.setInt(9, s.getProjWins());
                insertStmt.setInt(10, s.getProjLosses());
                insertStmt.setDate(11, sqlRunDate);

                insertStmt.addBatch();
            }

            insertStmt.executeBatch();
        }
    }
}