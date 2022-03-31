package com.unipi.largescale.entities;

import com.unipi.largescale.beans.CommentBean;
import com.unipi.largescale.dtos.CommentDto;
import com.unipi.largescale.dtos.CommentSubsetDto;

public class Comment {
    private String id;
    private String userId;
    private String name;
    private String surname;
    private String songId;
    private String text;


    public Comment(String userId, String songId, String name, String surname, String text){
        this.userId = userId;
        this.songId = songId;
        this.name = name;
        this.surname = surname;
        this.text = text;
    }

    public Comment(CommentDto comment){
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.songId = comment.getSongId();
        this.name = comment.getName();
        this.surname = comment.getSurname();
        this.text = comment.getText();
    }

    public Comment(CommentSubsetDto comment){
        this.id = comment.getCommentId();
        this.userId = comment.getUserId();
        this.name = comment.getName();
        this.surname = comment.getSurname();
        this.text = comment.getText();
    }

    public Comment(CommentBean comment){
        this.id = comment.getId();
        this.name = comment.getName();
        this.surname = comment.getSurname();
        this.text = comment.getText();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getText() {
        return text;
    }

    public String getSongId() {
        return songId;
    }

    public String getUserId() {
        return userId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}



