package com.myfantasy.dao;

import com.myfantasy.model.ScoringSetting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoringSettingDao {

    private static final String UPSERT_SQL =
        "MERGE INTO scoring_setting tgt " +
        "USING (SELECT ? AS league_id, ? AS stat_id, ? AS points FROM dual) src " +
        "ON (tgt.league_id = src.league_id AND tgt.stat_id = src.stat_id) " +
        "WHEN MATCHED THEN " +
        "UPDATE SET points = src.points " +
        "WHEN NOT MATCHED THEN " +
        "INSERT (league_id, stat_id, points) " +
        "VALUES (src.league_id, src.stat_id, src.points)";

    public void upsert(Connection conn, int leagueId, ScoringSetting setting) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPSERT_SQL)) {
            stmt.setInt(1, leagueId);
            stmt.setInt(2, setting.getStatId());
            stmt.setDouble(3, setting.getPoints());
            stmt.executeUpdate();
        }
    }
}