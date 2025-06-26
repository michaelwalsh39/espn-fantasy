package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.espnfantasy.model.Owner;

public class OwnerDao {

    private static final String UPSERT_SQL = 
        "MERGE INTO OWNER tgt " +
        "USING (SELECT ? AS league_id, ? AS owner_id, ? AS first_name, ? AS last_name, ? AS display_name, ? AS team_id FROM DUAL) src " +
        "ON (tgt.owner_id = src.owner_id AND tgt.league_id = src.league_id) " +
        "WHEN MATCHED THEN " +
        "UPDATE SET first_name = src.first_name, last_name = src.last_name, " +
        "display_name = src.display_name, team_id = src.team_id, modified_at = SYSTIMESTAMP " +
        "WHEN NOT MATCHED THEN " +
        "INSERT (league_id, owner_id, first_name, last_name, display_name, team_id) " +
        "VALUES (src.league_id, src.owner_id, src.first_name, src.last_name, src.display_name, src.team_id)";

    public void upsert(Connection conn, Owner owner, int leagueId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPSERT_SQL)) {
            stmt.setInt(1, leagueId);
            stmt.setString(2, owner.getId());
            stmt.setString(3, owner.getFirstName());
            stmt.setString(4, owner.getLastName());
            stmt.setString(5, owner.getDisplayName());
            stmt.setInt(6, owner.getTeamId());
            stmt.executeUpdate();
        }
    }
}