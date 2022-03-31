package com.unipi.large.scale.backend.dtos;

import com.unipi.large.scale.backend.entities.neo4j.FriendRequest;

import java.io.Serializable;

public class FriendRequestDto implements Serializable {

    public enum Status {
        UNKNOWN,
        ACCEPTED,
        REFUSED
    }

    public enum Type {
        INCOMING,
        OUTGOING
    }

    private FriendRequest.Type type;
    private FriendRequest.Status status;

    public FriendRequestDto() {
    }

    public FriendRequestDto(FriendRequest.Type type, FriendRequest.Status status) {
        this.type = type;
        this.status = status;
    }

    public FriendRequest.Type getType() {
        return type;
    }

    public void setType(FriendRequest.Type type) {
        this.type = type;
    }

    public FriendRequest.Status getStatus() {
        return status;
    }

    public void setStatus(FriendRequest.Status status) {
        this.status = status;
    }
}
