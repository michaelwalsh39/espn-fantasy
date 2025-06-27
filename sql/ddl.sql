CREATE TABLE sport (
    sport_id VARCHAR(10) NOT NULL PRIMARY KEY,
    sport_desc VARCHAR(25),
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

INSERT INTO sport (sport_id, sport_desc) VALUES ('flb', 'baseball');

CREATE TABLE league (
    league_id INTEGER NOT NULL PRIMARY KEY ,
    league_id_espn INTEGER,
    sport_id VARCHAR(10),
    season INTEGER,
    is_current NUMBER(1,0),
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

INSERT INTO league VALUES (1, 57274, 'flb', 2025, 1, SYSTIMESTAMP, SYSTIMESTAMP);

CREATE TABLE owner (
    league_id INTEGER NOT NULL,
    owner_id VARCHAR(50) NOT NULL,
    first_name VARCHAR(75),
    last_name VARCHAR(75),
    display_name VARCHAR(100),
    team_id INTEGER,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (league_id, owner_id)
);

CREATE TABLE team (
    league_id INTEGER,
    team_id INTEGER,
    team_name VARCHAR(255),
    team_abbrev VARCHAR(20),
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (league_id, team_id)
);

CREATE TABLE scoring_setting (
    league_id INTEGER NOT NULL,
    stat_id INTEGER NOT NULL,
    points NUMBER,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (league_id, stat_id)
);

CREATE TABLE matchup (
    league_id INTEGER NOT NULL,
    matchup_period_id INTEGER NOT NULL,
    matchup_id INTEGER NOT NULL,
    home_team_id INTEGER,
    away_team_id INTEGER,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (league_id, matchup_period_id, matchup_id)
);

CREATE TABLE matchup_scoring_period_team (
    league_id INTEGER NOT NULL,
    matchup_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    scoring_period_id INTEGER,
    stat_id NUMBER,
    points NUMBER,
    agg_type VARCHAR(20),
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

CREATE INDEX idx_matchup_scoring_period_team_comp
ON matchup_scoring_period_team (
    matchup_id,
    team_id,
    agg_type,
    league_id
);

CREATE TABLE matchup_scoring_period_player (
    league_id INTEGER NOT NULL,
    matchup_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    scoring_period_id INTEGER,
    stat_id NUMBER,
    points NUMBER,
    agg_type VARCHAR(20),
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    lineup_slot_id INTEGER
);

CREATE INDEX idx_matchup_scoring_period_player_comp
ON matchup_scoring_period_player (
    matchup_id,
    scoring_period_id,
    agg_type,
    league_id
);

CREATE TABLE player (
    league_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    position_id_default INTEGER,
    percent_owned NUMBER,
    pro_team_id INTEGER,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (league_id, player_id)
);

CREATE TABLE standings (
    league_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    wins INTEGER,
    losses INTEGER,
    ties INTEGER,
    points_for NUMBER,
    points_against NUMBER,
    playoff_pct NUMBER,
    proj_wins INTEGER,
    proj_losses INTEGER,
    run_date DATE NOT NULL,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (league_id, team_id, run_date)
);

CREATE TABLE draft (
    league_id INTEGER NOT NULL,
    season INTEGER NOT NULL,
    team_id INTEGER,
    pick_number_overall INTEGER NOT NULL,
    round INTEGER,
    pick_number_round INTEGER,
    is_keeper NUMBER(1,0),
    is_reserved_for_keeper NUMBER(1,0),
    player_id INTEGER,
    autodraft_type_id INTEGER,
    nominating_team_id INTEGER,
    bid_amount NUMBER,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (league_id, season, pick_number_overall)
);

CREATE TABLE position (
    sport_id VARCHAR(10) NOT NULL,
    position_id INTEGER NOT NULL,
    position_name VARCHAR(25) NULL,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (sport_id, position_id)
);

INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 0, 'C');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 1, '1B');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 2, '2B');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 3, '3B');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 4, 'SS');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 5, 'OF');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 6, '2B/SS');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 7, '1B/3B');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 8, 'LF');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 9, 'CF');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 10, 'RF');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 11, 'DH');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 12, 'UTIL');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 13, 'P');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 14, 'SP');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 15, 'RP');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 16, 'BE');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 17, 'IL');
INSERT INTO position (sport_id, position_id, position_name) VALUES ('flb', 19, 'IF');

CREATE TABLE stat (
    sport_id VARCHAR(10) NOT NULL,
    stat_id INTEGER NOT NULL,
    stat_name VARCHAR(25) NULL,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (sport_id, stat_id)
);

INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 0, 'AB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 1, 'H');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 2, 'AVG');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 3, '2B');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 4, '3B');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 5, 'HR');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 6, 'XBH');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 7, '1B');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 8, 'TB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 9, 'SLG');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 10, 'B_BB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 11, 'B_IBB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 12, 'HBP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 13, 'SF');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 14, 'SH');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 15, 'SAC');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 16, 'PA');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 17, 'OBP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 18, 'OPS');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 19, 'RC');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 20, 'R');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 21, 'RBI');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 23, 'SB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 24, 'CS');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 25, 'SB-CS');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 26, 'GDP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 27, 'B_SO');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 28, 'PS');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 29, 'PPA');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 31, 'CYC');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 32, 'GP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 33, 'GS');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 34, 'OUTS');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 35, 'TBF');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 36, 'P');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 37, 'P_H');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 38, 'OBA');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 39, 'P_BB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 40, 'P_IBB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 41, 'WHIP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 42, 'HBP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 43, 'OOBP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 44, 'P_R');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 45, 'ER');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 46, 'P_HR');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 47, 'ERA');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 48, 'K');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 49, 'K/9');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 50, 'WP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 51, 'BLK');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 52, 'PK');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 53, 'W');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 54, 'L');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 55, 'WPCT');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 56, 'SVO');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 57, 'SV');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 58, 'BLSV');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 59, 'SV%');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 60, 'HLD');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 62, 'CG');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 63, 'QS');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 65, 'NH');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 66, 'PG');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 67, 'TC');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 68, 'PO');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 69, 'A');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 70, 'OFA');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 71, 'FPCT');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 72, 'E');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 73, 'DP');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 74, 'B_G_W');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 75, 'B_G_L');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 76, 'P_G_W');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 77, 'P_G_L');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 81, 'G');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 82, 'K/BB');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 83, 'SVHD');
INSERT INTO stat (sport_id, stat_id, stat_name) VALUES ('flb', 99, 'STARTER');
