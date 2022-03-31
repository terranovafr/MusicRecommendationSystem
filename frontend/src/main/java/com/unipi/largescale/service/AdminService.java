package com.unipi.largescale.service;

import com.unipi.largescale.API.API;
import com.unipi.largescale.entities.aggregations.Country;

import java.util.List;
import java.util.Objects;

public class AdminService {
    public static int getClusterHighestVariance(){
        return API.getClusterHighestVariance();
    }

    public static int getMostDanceableCluster(){
        return Objects.requireNonNull(API.getMostDanceableCluster()).getId();
    }

    public static List<Country> getTopKCountries(){
        return API.getTopKCountries(3);
    }

}
