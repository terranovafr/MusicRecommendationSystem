package com.unipi.largescale.dtos;

import java.io.Serializable;

public class CommentSubsetDto implements Serializable {

    private String commentId;
    private String userId;
    private String name;
    private String surname;
    private String text;

    public CommentSubsetDto() {
    }

    public CommentSubsetDto(String commentId, String userId, String name, String surname, String text) {
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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}