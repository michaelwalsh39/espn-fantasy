package com.myfantasy.model;

public class ScoringSetting {
    private final int statId;
    private final double points;

    public ScoringSetting(int statId, double points) {
        this.statId = statId;
        this.points = points;
    }

    public int getStatId() {
        return statId;
    }

    public double getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "ScoringRule{" +
                "statId=" + statId +
                ", points=" + points +
                '}';
    }
}
