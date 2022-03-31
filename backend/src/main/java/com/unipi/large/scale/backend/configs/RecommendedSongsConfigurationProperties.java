package com.unipi.large.scale.backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "recommended.songs")
public class RecommendedSongsConfigurationProperties {

    @Value("${recommended.songs.alpha}")
    private double alpha;

    @Value("${recommended.songs.beta}")
    private double beta;

    public RecommendedSongsConfigurationProperties() {
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }
}
