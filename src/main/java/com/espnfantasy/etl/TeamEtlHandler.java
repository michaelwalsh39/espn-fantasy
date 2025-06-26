package com.espnfantasy.etl;

import com.espnfantasy.dao.OwnerDao;
import com.espnfantasy.dao.TeamDao;
import com.espnfantasy.model.Owner;
import com.espnfantasy.model.Standings;
import com.espnfantasy.model.Team;
import com.espnfantasy.store.DataStore;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class TeamEtlHandler implements EtlHandler {
    private final int leagueId;

    public TeamEtlHandler(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public void handle(JsonNode root, DataStore dataStore, Connection conn) throws Exception {
        Map<String, Integer> ownerToTeamMap = new HashMap<>();
        TeamDao teamDao = new TeamDao();
        OwnerDao ownerDao = new OwnerDao();

        if (root.has("teams")) {
            for (JsonNode teamNode : root.get("teams")) {
                int teamId = teamNode.path("id").asInt();
                String name = teamNode.path("name").asText();
                String abbrev = teamNode.path("abbrev").asText();
                String primaryOwner = teamNode.path("primaryOwner").asText();

                Team team = new Team(teamId, name, abbrev, primaryOwner);
                dataStore.addTeam(team);
                teamDao.upsert(conn, team, leagueId);

                // Track owner-to-team mapping
                if (team.getPrimaryOwner() != null) {
                    ownerToTeamMap.put(team.getPrimaryOwner(), team.getId());
                }
            }
        }

        if (root.has("members")) {
            for (JsonNode memberNode : root.get("members")) {
                String ownerId = memberNode.path("id").asText();
                String displayName = memberNode.path("displayName").asText();
                String firstName = memberNode.path("firstName").asText();
                String lastName = memberNode.path("lastName").asText();

                Owner owner = new Owner(ownerId, firstName, lastName, displayName);
                if (ownerToTeamMap.containsKey(owner.getId())) {
                    owner.setTeamId(ownerToTeamMap.get(owner.getId()));
                }
                dataStore.addOwner(owner);
                ownerDao.upsert(conn, owner, leagueId);
            }
        }

        // save standings to dataStore, but don't load to db
        // we'll add some extra info from a different endpoint and load there
        if (root.has("teams")) {
            for (JsonNode teamNode : root.get("teams")) {
                int teamId = teamNode.path("id").asInt();
                int wins = teamNode.path("record").path("overall").path("wins").asInt();
                int losses = teamNode.path("record").path("overall").path("losses").asInt();
                int ties = teamNode.path("record").path("overall").path("ties").asInt();
                double pointsFor = teamNode.path("record").path("overall").path("pointsFor").asDouble();
                double pointsAgainst = teamNode.path("record").path("overall").path("pointsAgainst").asDouble();

                Standings standing = new Standings(leagueId, teamId, wins, losses, ties, pointsFor, pointsAgainst, null, null, null, null);
                dataStore.addStandings(standing);
            }
        }

    }
}
