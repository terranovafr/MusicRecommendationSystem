package com.unipi.largescale.entities;

import com.unipi.largescale.dtos.SurveyDto;

public class Survey {

    private double extraversion;
    private double agreeableness;
    private double conscientiousness;
    private double neuroticism;
    private double openness;
    private double timeSpent;

    public Survey() {
    }

    public Survey(double extraversion, double agreeableness, double conscientiousness, double neuroticism, double openness, double timeSpent) {
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.conscientiousness = conscientiousness;
        this.neuroticism = neuroticism;
        this.openness = openness;
        this.timeSpent = timeSpent;
    }

    public Survey(SurveyDto survey) {
        this.extraversion = survey.getExtraversion();
        this.agreeableness = survey.getAgreeableness();
        this.conscientiousness = survey.getConscientiousness();
        this.neuroticism = survey.getNeuroticism();
        this.openness = survey.getOpenness();
        this.timeSpent = survey.getTimeSpent();
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
