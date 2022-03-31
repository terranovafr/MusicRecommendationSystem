package com.unipi.large.scale.backend.data;

import com.unipi.large.scale.backend.entities.mongodb.MongoUser;

public class Survey {

    private double extraversion;
    private double agreeableness;
    private double conscientiousness;
    private double neuroticism;
    private double openness;
    private double timeSpent;

    public Survey() {
    }

    public Survey(MongoUser user) {
        this.extraversion = user.getExtraversion();
        this.agreeableness = user.getAgreeableness();
        this.conscientiousness = user.getConscientiousness();
        this.neuroticism = user.getNeuroticism();
        this.openness = user.getOpenness();
        this.timeSpent = user.getTimeSpent();
    }

    public Survey(double extraversion, double agreeableness, double conscientiousness, double neuroticism, double openness, double timeSpent) {
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.conscientiousness = conscientiousness;
        this.neuroticism = neuroticism;
        this.openness = openness;
        this.timeSpent = timeSpent;
    }

    public double getExtraversion() {
        return extraversion;
    }

    public void setExtraversion(double extraversion) {
        this.extraversion = extraversion;
    }

    public double getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(double agreeableness) {
        this.agreeableness = agreeableness;
    }

    public double getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(double conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public double getNeuroticism() {
        return neuroticism;
    }

    public void setNeuroticism(double neuroticism) {
        this.neuroticism = neuroticism;
    }

    public double getOpenness() {
        return openness;
    }

    public void setOpenness(double openness) {
        this.openness = openness;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }
}
