package com.espnfantasy.etl;

import com.espnfantasy.dao.PlayerDao;
import com.espnfantasy.model.Player;
import com.espnfantasy.store.DataStore;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;

public class PlayerEtlHandler implements EtlHandler {
    private final int leagueId;

    public PlayerEtlHandler(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public void handle(JsonNode data, DataStore dataStore, Connection conn) throws Exception {
        JsonNode players = data.get("players");
        if (players == null || !players.isArray()) return;

        for (JsonNode p : players) {
            JsonNode player = p.get("player");

            if (player == null) continue;

            int id = player.get("id").asInt();
            String firstName = player.get("firstName").asText("");
            String lastName = player.get("lastName").asText("");
            Integer defaultPositionId = player.get("defaultPositionId").asInt();
            Double percentOwned = player.path("ownership").path("percentOwned").asDouble(0.0);
            Integer proTeamId = player.get("proTeamId").asInt();

            Player dsPlayer = new Player(id, firstName, lastName, defaultPositionId, percentOwned, proTeamId);
            dataStore.addPlayer(dsPlayer);
        }

        PlayerDao playerDao = new PlayerDao();
        playerDao.upsert(conn, dataStore.getPlayerList(), leagueId);
    }
}