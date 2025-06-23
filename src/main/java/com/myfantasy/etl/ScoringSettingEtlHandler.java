package com.myfantasy.etl;

import com.myfantasy.model.ScoringSetting;
import com.myfantasy.store.DataStore;
import com.myfantasy.dao.ScoringSettingDao;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;

public class ScoringSettingEtlHandler implements EtlHandler {
    private final int leagueId;

    public ScoringSettingEtlHandler(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public void handle(JsonNode root, DataStore dataStore, Connection conn) throws Exception {
        JsonNode scoringItems = root
            .path("settings")
            .path("scoringSettings")
            .path("scoringItems");
        
        ScoringSettingDao dao = new ScoringSettingDao();

        if (scoringItems.isArray()) {
            for (JsonNode item : scoringItems) {
                int statId = item.path("statId").asInt();
                double points = item.path("points").asDouble();
                // System.out.println("Stat ID: " + statId + ", Points: " + points);

                ScoringSetting rule = new ScoringSetting(statId, points);
                dataStore.addScoringRule(rule);
                dao.upsert(conn, leagueId, rule);         
            }
        } else {
            System.out.println("scoringItems is not an array or missing");
        }
    }
}