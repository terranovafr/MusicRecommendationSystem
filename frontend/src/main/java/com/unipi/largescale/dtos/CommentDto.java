package com.unipi.largescale.dtos;

import com.unipi.largescale.entities.Comment;

import java.io.Serializable;

public class CommentDto implements Serializable {
    private String id;
    private String userId;
    private String name;
    private String surname;
    private String songId;
    private String text;

    public CommentDto() {
    }

    public CommentDto(String id, String userId, String name, String surname, String songId, String text) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.songId = songId;
        this.text = text;
    }

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.name = comment.getName();
        this.surname = comment.getSurname();
        this.songId = comment.getSongId();
        this.text = comment.getText();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
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
}
