package com.unipi.large.scale.backend.dtos;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CommentDto implements Serializable {

    private String id;
    @NotNull
    @NotBlank
    private String userId;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String surname;
    @NotNull
    @NotBlank
    private String songId;
    @NotNull
    @NotBlank
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdSerializer(ObjectId id) {
        this.id = id.toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserIdSerializer(ObjectId userId) {
        this.userId = userId.toString();
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public void setSongIdSerializer(ObjectId songId) {
        this.songId = songId.toString();
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
