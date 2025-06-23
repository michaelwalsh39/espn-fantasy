package com.myfantasy.etl;

import java.sql.Connection;

import com.fasterxml.jackson.databind.JsonNode;
import com.myfantasy.store.DataStore;

public interface EtlHandler {
    void handle(JsonNode root, DataStore dataStore, Connection conn) throws Exception;
}
