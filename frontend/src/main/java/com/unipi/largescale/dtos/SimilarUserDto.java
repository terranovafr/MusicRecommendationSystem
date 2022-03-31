package com.unipi.largescale.dtos;

import java.io.Serializable;

public class SimilarUserDto implements Serializable {

    private Long id;
    private InterfaceUserDto user;
    private double weight;

    public SimilarUserDto() {
    }

    public SimilarUserDto(Long id, InterfaceUserDto userDto, double weight) {
        this.id = id;
        this.user = userDto;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InterfaceUserDto getUser() {
        return user;
    }

    public void setUser(InterfaceUserDto user) {
        this.user = user;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
