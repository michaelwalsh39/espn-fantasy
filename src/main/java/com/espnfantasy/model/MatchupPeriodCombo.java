package com.espnfantasy.model;

import java.util.Objects;

public class MatchupPeriodCombo {
    private int matchupPeriodId;
    private int scoringPeriodId;

    public MatchupPeriodCombo(int matchupPeriodId, int scoringPeriodId) {
        this.matchupPeriodId = matchupPeriodId;
        this.scoringPeriodId = scoringPeriodId;
    }

    public int getMatchupPeriodId() {
        return matchupPeriodId;
    }

    public int getScoringPeriodId() {
        return scoringPeriodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchupPeriodCombo)) return false;
        MatchupPeriodCombo that = (MatchupPeriodCombo) o;
        return matchupPeriodId == that.matchupPeriodId &&
               scoringPeriodId == that.scoringPeriodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchupPeriodId, scoringPeriodId);
    }
}
