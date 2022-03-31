package com.unipi.largescale.entities;

import com.unipi.largescale.dtos.FriendRequestDto;

public class FriendRequest {

    private String type;
    private String status;


    public FriendRequest(FriendRequestDto friendRequestDto){
        this.type = friendRequestDto.getType();
        this.status = friendRequestDto.getStatus();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus(){ return status; }

    public void setStatus(String status) {
        this.status = status;
    }

}
