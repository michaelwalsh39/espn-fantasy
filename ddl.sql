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
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP
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

CREATE TABLE stat (
    sport_id VARCHAR(10) NOT NULL,
    stat_id INTEGER NOT NULL,
    stat_name VARCHAR(25) NULL,
    added_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (sport_id, stat_id)
);

INSERT INTO stat (stat_id, stat_name) VALUES (0, 'AB');
INSERT INTO stat (stat_id, stat_name) VALUES (1, 'H');
INSERT INTO stat (stat_id, stat_name) VALUES (2, 'AVG');
INSERT INTO stat (stat_id, stat_name) VALUES (3, '2B');
INSERT INTO stat (stat_id, stat_name) VALUES (4, '3B');
INSERT INTO stat (stat_id, stat_name) VALUES (5, 'HR');
INSERT INTO stat (stat_id, stat_name) VALUES (6, 'XBH');
INSERT INTO stat (stat_id, stat_name) VALUES (7, '1B');
INSERT INTO stat (stat_id, stat_name) VALUES (8, 'TB');
INSERT INTO stat (stat_id, stat_name) VALUES (9, 'SLG');
INSERT INTO stat (stat_id, stat_name) VALUES (10, 'B_BB');
INSERT INTO stat (stat_id, stat_name) VALUES (11, 'B_IBB');
INSERT INTO stat (stat_id, stat_name) VALUES (12, 'HBP');
INSERT INTO stat (stat_id, stat_name) VALUES (13, 'SF');
INSERT INTO stat (stat_id, stat_name) VALUES (14, 'SH');
INSERT INTO stat (stat_id, stat_name) VALUES (15, 'SAC');
INSERT INTO stat (stat_id, stat_name) VALUES (16, 'PA');
INSERT INTO stat (stat_id, stat_name) VALUES (17, 'OBP');
INSERT INTO stat (stat_id, stat_name) VALUES (18, 'OPS');
INSERT INTO stat (stat_id, stat_name) VALUES (19, 'RC');
INSERT INTO stat (stat_id, stat_name) VALUES (20, 'R');
INSERT INTO stat (stat_id, stat_name) VALUES (21, 'RBI');
INSERT INTO stat (stat_id, stat_name) VALUES (23, 'SB');
INSERT INTO stat (stat_id, stat_name) VALUES (24, 'CS');
INSERT INTO stat (stat_id, stat_name) VALUES (25, 'SB-CS');
INSERT INTO stat (stat_id, stat_name) VALUES (26, 'GDP');
INSERT INTO stat (stat_id, stat_name) VALUES (27, 'B_SO');
INSERT INTO stat (stat_id, stat_name) VALUES (28, 'PS');
INSERT INTO stat (stat_id, stat_name) VALUES (29, 'PPA');
INSERT INTO stat (stat_id, stat_name) VALUES (31, 'CYC');
INSERT INTO stat (stat_id, stat_name) VALUES (32, 'GP');
INSERT INTO stat (stat_id, stat_name) VALUES (33, 'GS');
INSERT INTO stat (stat_id, stat_name) VALUES (34, 'OUTS');
INSERT INTO stat (stat_id, stat_name) VALUES (35, 'TBF');
INSERT INTO stat (stat_id, stat_name) VALUES (36, 'P');
INSERT INTO stat (stat_id, stat_name) VALUES (37, 'P_H');
INSERT INTO stat (stat_id, stat_name) VALUES (38, 'OBA');
INSERT INTO stat (stat_id, stat_name) VALUES (39, 'P_BB');
INSERT INTO stat (stat_id, stat_name) VALUES (40, 'P_IBB');
INSERT INTO stat (stat_id, stat_name) VALUES (41, 'WHIP');
INSERT INTO stat (stat_id, stat_name) VALUES (42, 'HBP');
INSERT INTO stat (stat_id, stat_name) VALUES (43, 'OOBP');
INSERT INTO stat (stat_id, stat_name) VALUES (44, 'P_R');
INSERT INTO stat (stat_id, stat_name) VALUES (45, 'ER');
INSERT INTO stat (stat_id, stat_name) VALUES (46, 'P_HR');
INSERT INTO stat (stat_id, stat_name) VALUES (47, 'ERA');
INSERT INTO stat (stat_id, stat_name) VALUES (48, 'K');
INSERT INTO stat (stat_id, stat_name) VALUES (49, 'K/9');
INSERT INTO stat (stat_id, stat_name) VALUES (50, 'WP');
INSERT INTO stat (stat_id, stat_name) VALUES (51, 'BLK');
INSERT INTO stat (stat_id, stat_name) VALUES (52, 'PK');
INSERT INTO stat (stat_id, stat_name) VALUES (53, 'W');
INSERT INTO stat (stat_id, stat_name) VALUES (54, 'L');
INSERT INTO stat (stat_id, stat_name) VALUES (55, 'WPCT');
INSERT INTO stat (stat_id, stat_name) VALUES (56, 'SVO');
INSERT INTO stat (stat_id, stat_name) VALUES (57, 'SV');
INSERT INTO stat (stat_id, stat_name) VALUES (58, 'BLSV');
INSERT INTO stat (stat_id, stat_name) VALUES (59, 'SV%');
INSERT INTO stat (stat_id, stat_name) VALUES (60, 'HLD');
INSERT INTO stat (stat_id, stat_name) VALUES (62, 'CG');
INSERT INTO stat (stat_id, stat_name) VALUES (63, 'QS');
INSERT INTO stat (stat_id, stat_name) VALUES (65, 'NH');
INSERT INTO stat (stat_id, stat_name) VALUES (66, 'PG');
INSERT INTO stat (stat_id, stat_name) VALUES (67, 'TC');
INSERT INTO stat (stat_id, stat_name) VALUES (68, 'PO');
INSERT INTO stat (stat_id, stat_name) VALUES (69, 'A');
INSERT INTO stat (stat_id, stat_name) VALUES (70, 'OFA');
INSERT INTO stat (stat_id, stat_name) VALUES (71, 'FPCT');
INSERT INTO stat (stat_id, stat_name) VALUES (72, 'E');
INSERT INTO stat (stat_id, stat_name) VALUES (73, 'DP');
INSERT INTO stat (stat_id, stat_name) VALUES (74, 'B_G_W');
INSERT INTO stat (stat_id, stat_name) VALUES (75, 'B_G_L');
INSERT INTO stat (stat_id, stat_name) VALUES (76, 'P_G_W');
INSERT INTO stat (stat_id, stat_name) VALUES (77, 'P_G_L');
INSERT INTO stat (stat_id, stat_name) VALUES (81, 'G');
INSERT INTO stat (stat_id, stat_name) VALUES (82, 'K/BB');
INSERT INTO stat (stat_id, stat_name) VALUES (83, 'SVHD');
INSERT INTO stat (stat_id, stat_name) VALUES (99, 'STARTER');
