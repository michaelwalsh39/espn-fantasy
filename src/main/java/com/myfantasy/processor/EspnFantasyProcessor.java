package com.myfantasy.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.myfantasy.api.EspnApiClient;
import com.myfantasy.dao.MatchupScoringPlayerDao;
import com.myfantasy.dao.MatchupScoringTeamDao;
import com.myfantasy.etl.DraftEtlHandler;
import com.myfantasy.etl.PlayerEtlHandler;
import com.myfantasy.etl.TeamEtlHandler;
import com.myfantasy.etl.ScoringSettingEtlHandler;
import com.myfantasy.etl.StandingsEtlHandler;
import com.myfantasy.etl.TeamMatchupEtlHandler;
import com.myfantasy.store.DataStore;
import java.util.Properties;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EspnFantasyProcessor {
    private final EspnApiClient apiClient;
    private final DataStore dataStore;
    private final Connection conn;
    private final int leagueId;
    private final Properties props;

    public EspnFantasyProcessor(EspnApiClient apiClient, int leagueId, Connection conn, Properties props) {
        this.apiClient = apiClient;
        this.dataStore = new DataStore();
        this.conn = conn;
        this.leagueId = leagueId;
        this.props = props;

        // manually set each endpoint (and any required filters) we want to process
        dataStore.addEtlRegistryEntry("mTeam", "{}", new TeamEtlHandler(leagueId));
        dataStore.addEtlRegistryEntry("mSettings", "{}", new ScoringSettingEtlHandler(leagueId));
        dataStore.addEtlRegistryEntry("mMatchup", "{}", new TeamMatchupEtlHandler(leagueId));
        dataStore.addEtlRegistryEntry("players_wl", "{}", new PlayerEtlHandler(leagueId));
        dataStore.addEtlRegistryEntry("mStandings", "{}", new StandingsEtlHandler(leagueId));
        dataStore.addEtlRegistryEntry("mDraftDetail", "{}", new DraftEtlHandler(leagueId));
    }

    public void fetchAndHandle() throws Exception {
        List<DataStore.EtlRegistryEntry> registry = dataStore.getEtlRegistry();
        Set<String> processed = new HashSet<>();
        int index = 0;
    
        while (index < registry.size()) {
            DataStore.EtlRegistryEntry entry = registry.get(index++);

            if (processed.contains(entry.getView())) continue;
            processed.add(entry.getView());

            System.out.println("Running: " + entry.getView());
            JsonNode data = apiClient.fetch(entry.getView(), entry.getFilter(), props);
            entry.getEtlHandler().handle(data, dataStore, conn);
        }

    }

    public void runAggs() throws Exception {
        MatchupScoringTeamDao scoringTeamDao = new MatchupScoringTeamDao();
        scoringTeamDao.upsertAggregatedStats(conn, "daily", "weekly", leagueId);

        MatchupScoringPlayerDao scoringPlayerDao = new MatchupScoringPlayerDao();
        scoringPlayerDao.upsertAggregatedStats(conn, "stat_daily", "stat_weekly", leagueId);
        scoringPlayerDao.upsertAggregatedStats(conn, "stat_daily", "daily", leagueId);
        scoringPlayerDao.upsertAggregatedStats(conn, "daily", "weekly", leagueId);
    }
}
