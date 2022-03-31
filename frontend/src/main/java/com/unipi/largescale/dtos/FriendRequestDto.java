package com.unipi.largescale.dtos;

import java.io.Serializable;

public class FriendRequestDto implements Serializable {

    private String type;
    private String status;

    public FriendRequestDto() {
    }

    public FriendRequestDto(String type, String status) {
        this.type = type;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
