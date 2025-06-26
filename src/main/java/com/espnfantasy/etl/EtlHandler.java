package com.espnfantasy.etl;

import java.sql.Connection;

import com.espnfantasy.store.DataStore;
import com.fasterxml.jackson.databind.JsonNode;

public interface EtlHandler {
    void handle(JsonNode root, DataStore dataStore, Connection conn) throws Exception;
}
