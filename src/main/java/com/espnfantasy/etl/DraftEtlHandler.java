package com.espnfantasy.etl;

import com.espnfantasy.dao.DraftDao;
import com.espnfantasy.model.Draft;
import com.espnfantasy.store.DataStore;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DraftEtlHandler implements EtlHandler {
    private final int leagueId;

    public DraftEtlHandler(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public void handle(JsonNode root, DataStore dataStore, Connection conn) throws Exception {
        JsonNode detail = root.path("draftDetail");
        long completeDateMillis = detail.path("completeDate").asLong();
        LocalDate completedDate = Instant.ofEpochMilli(completeDateMillis)
                                         .atZone(ZoneId.systemDefault())
                                         .toLocalDate();
        int season = completedDate.getYear();

        List<Draft> drafts = new ArrayList<>();
        for (JsonNode pick : detail.path("picks")) {
            Draft draft = new Draft();
            draft.setLeagueId(leagueId);
            draft.setSeason(season);
            draft.setCompletedDate(completedDate);
            draft.setTeamId(pick.path("teamId").asInt());
            draft.setOverallPickNumber(pick.path("overallPickNumber").asInt());
            draft.setRound(pick.path("roundId").asInt());
            draft.setRoundPickNumber(pick.path("roundPickNumber").asInt());
            draft.setKeeper(pick.path("keeper").asBoolean());
            draft.setReservedForKeeper(pick.path("reservedForKeeper").asBoolean());
            draft.setPlayerId(pick.path("playerId").asInt());

            if (pick.has("autoDraftTypeId") && !pick.path("autoDraftTypeId").isNull()) {
                draft.setAutodraftTypeId(pick.path("autoDraftTypeId").asInt());
            }

            if (pick.has("nominatingTeamId") && !pick.path("nominatingTeamId").isNull()) {
                draft.setNominatingTeamId(pick.path("nominatingTeamId").asInt());
            }

            if (pick.has("bidAmount") && !pick.path("bidAmount").isNull()) {
                draft.setBidAmount(pick.path("bidAmount").asDouble());
            }

            drafts.add(draft);
        }

        dataStore.getDrafts().addAll(drafts);
        DraftDao draftDao = new DraftDao();
        draftDao.upsert(conn, dataStore.getDrafts(), leagueId);
    }
}
