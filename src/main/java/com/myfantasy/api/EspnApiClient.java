package com.myfantasy.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EspnApiClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private final String sport;
    private final String season;
    private final String espnLeagueId;

    public EspnApiClient(String sport, String season, String espnLeagueId) {
        this.sport = sport;
        this.season = season;
        this.espnLeagueId = espnLeagueId;
    }

    public JsonNode fetch(String view, String filter, Properties props) throws Exception {
        String swid = props.getProperty("swid");
        String espnS2 = props.getProperty("espn_s2");

        String url = buildUrl(view);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Cookie", "espn_s2=" + espnS2 + "; SWID=" + swid)
                .header("x-fantasy-filter", filter)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch ESPN data. Status: " + response.statusCode());
        }

        return mapper.readTree(response.body());
    }

    private String buildUrl(String view) {
        String base = String.format(
            "https://lm-api-reads.fantasy.espn.com/apis/v3/games/%s/seasons/%s/segments/0/leagues/%s",
            sport, season, espnLeagueId
        );

        if (view == null || view.isEmpty()) return base;

        String url = base + "?view=" + view;

        return url;
    }
}
