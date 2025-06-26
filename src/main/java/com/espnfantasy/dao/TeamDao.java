package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.espnfantasy.model.Team;

public class TeamDao {

    private static final String UPSERT_SQL =
        // need to change hard-coded leagueId 
        "MERGE INTO TEAM tgt " +
        "USING (SELECT ? AS league_id, ? AS team_id, ? AS team_name, ? AS team_abbrev FROM DUAL) src " +
        "ON (tgt.team_id = src.team_id AND tgt.league_id = src.league_id) " +
        "WHEN MATCHED THEN " +
        "UPDATE SET team_name = src.team_name, " +
        "team_abbrev = src.team_abbrev, modified_at = SYSTIMESTAMP " +
        "WHEN NOT MATCHED THEN " +
        "INSERT (league_id, team_id, team_name, team_abbrev) " +
        "VALUES (src.league_id, src.team_id, src.team_name, src.team_abbrev)";

    public void upsert(Connection conn, Team team, int leagueId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPSERT_SQL)) {
            stmt.setInt(1, leagueId);
            stmt.setInt(2, team.getId());
            stmt.setString(3, team.getName());
            stmt.setString(4, team.getAbbrev());
            stmt.executeUpdate();
        }
    }
}