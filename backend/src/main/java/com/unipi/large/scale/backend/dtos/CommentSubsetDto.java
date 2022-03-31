package com.unipi.large.scale.backend.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.io.Serializable;

public class CommentSubsetDto implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId commentId;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    private String name;
    private String surname;
    private String text;

    public CommentSubsetDto() {
    }

    public CommentSubsetDto(ObjectId commentId, ObjectId userId, String name, String surname, String text) {
        this.commentId = commentId;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ObjectId getCommentId() {
        return commentId;
    }

    public void setCommentId(ObjectId commentId) {
        this.commentId = commentId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}