package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.espnfantasy.model.Draft;

public class DraftDao {

    public void upsert(Connection conn, List<Draft> drafts, int leagueId) throws SQLException {
        Draft first = drafts.get(0);
        int season = first.getSeason();

        String deleteSql = "DELETE FROM draft WHERE league_id = ? AND season = ?";
        String insertSql = "INSERT INTO draft " +
                "(league_id, season, team_id, pick_number_overall, round, pick_number_round, is_keeper, is_reserved_for_keeper, " +
                "player_id, autodraft_type_id, nominating_team_id, bid_amount, added_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";

        try (
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql)
        ) {
            deleteStmt.setInt(1, leagueId);
            deleteStmt.setInt(2, season);
            deleteStmt.executeUpdate();

            for (Draft d : drafts) {
                insertStmt.setInt(1, d.getLeagueId());
                insertStmt.setInt(2, d.getSeason());
                insertStmt.setInt(3, d.getTeamId());
                insertStmt.setInt(4, d.getOverallPickNumber());
                insertStmt.setInt(5, d.getRound());
                insertStmt.setInt(6, d.getRoundPickNumber());
                insertStmt.setInt(7, d.isKeeper() ? 1 : 0);
                insertStmt.setInt(8, d.isReservedForKeeper() ? 1 : 0);
                insertStmt.setInt(9, d.getPlayerId());

                if (d.getAutodraftTypeId() != null) {
                    insertStmt.setInt(10, d.getAutodraftTypeId());
                } else {
                    insertStmt.setNull(10, java.sql.Types.INTEGER);
                }

                if (d.getNominatingTeamId() != null && d.getNominatingTeamId() != 0) {
                    insertStmt.setInt(11, d.getNominatingTeamId());
                } else {
                    insertStmt.setNull(11, java.sql.Types.INTEGER);
                }

                if (d.getBidAmount() != null && d.getBidAmount() != 0) {
                    insertStmt.setDouble(12, d.getBidAmount());
                } else {
                    insertStmt.setNull(12, java.sql.Types.DOUBLE);
                }

                insertStmt.addBatch();
            }

            insertStmt.executeBatch();
        }
    }
}
