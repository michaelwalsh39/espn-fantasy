package com.myfantasy;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.myfantasy.api.EspnApiClient;
import com.myfantasy.config.ConfigContext;
import com.myfantasy.dao.ConfigDao;
import com.myfantasy.processor.EspnFantasyProcessor;

public class EspnFantasyApp {
    public static void main(String[] args) {
        try {
            // read in creds
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            String jdbcUrl = String.format(props.getProperty("jdbc_url"));
            Connection conn = DriverManager.getConnection(
                jdbcUrl, props.getProperty("jdbc_user"), props.getProperty("jdbc_pword")
            );

            // reads in config params from database
            // sets league_id, espn_league_id, etc.
            ConfigDao configDao = new ConfigDao();
            ConfigContext config = configDao.loadConfig(conn);

            EspnApiClient apiClient = new EspnApiClient(
                config.getSport(),
                config.getSeason(),
                config.getEspnLeagueId()
            );

            EspnFantasyProcessor processor = new EspnFantasyProcessor(
                apiClient,
                config.getLeagueId(),
                conn,
                props
            );
            processor.fetchAndHandle();
            processor.runAggs();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}