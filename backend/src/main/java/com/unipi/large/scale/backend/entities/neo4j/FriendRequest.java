package com.unipi.large.scale.backend.entities.neo4j;

public class FriendRequest {

    public enum Status {
        UNKNOWN,
        ACCEPTED,
        REFUSED
    }

    public enum Type {
        INCOMING,
        OUTGOING
    }

    private Type type;
    private Status status;

    public FriendRequest() {
    }

    public FriendRequest(Type type, Status status) {
        this.type = type;
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "type=" + type +
                ", status=" + status +
                '}';
    }
}
