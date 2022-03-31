package com.unipi.large.scale.backend.entities.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Objects;

@Document
public class CommentSubset implements Comparable<CommentSubset>{

    @Field("comment_id")
    private ObjectId commentId;
    @Field("user_id")
    private ObjectId userId;
    private String name;
    private String surname;
    private String text;
    private LocalDate date;

    public CommentSubset() {
    }

    public CommentSubset(ObjectId commentId, ObjectId userId, String name, String surname, String text, LocalDate date) {
        this.commentId = commentId;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.text = text;
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentSubset that = (CommentSubset) o;
        return commentId.equals(that.commentId) && userId.equals(that.userId) && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, userId, text);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int compareTo(CommentSubset o) {
        return getDate().compareTo(o.getDate());
    }
}