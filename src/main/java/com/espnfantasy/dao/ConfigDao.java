package com.espnfantasy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.espnfantasy.config.ConfigContext;

public class ConfigDao {

    public ConfigContext loadConfig(Connection conn) throws SQLException {
        String query = "SELECT league_id, sport_id, season, league_id_espn FROM league WHERE is_current = 1";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int leagueId = rs.getInt("league_id");
                String sport = rs.getString("sport_id");
                String season = rs.getString("season");
                String espnLeagueId = rs.getString("league_id_espn");

                return new ConfigContext(leagueId, sport, season, espnLeagueId);
            } else {
                throw new IllegalStateException("No config found in the database.");
            }
        }
    }

    public int loadRecentMatchupPeriodId(int leagueId, Connection conn) throws SQLException {
        String query = "SELECT" +
                        "  COALESCE(MAX(m.MATCHUP_PERIOD_ID), 1) AS matchup_period_id " +
                        "FROM matchup_scoring_period_player p " +
                        "JOIN matchup m " +
                        "  ON p.matchup_id = m.matchup_id " +
                        "WHERE m.league_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, leagueId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int matchupPeriodId = rs.getInt("matchup_period_id");

                    if (matchupPeriodId == 1) { // if it's early in the season, do nothing
                        ;
                    }
                    else {
                        matchupPeriodId --; // if it's not, look back a full week from last load
                    }
                    return matchupPeriodId; 

                } else {
                    throw new IllegalStateException("No recent matchupPeriodId found in the database.");
                }
            }
        }
    }
}