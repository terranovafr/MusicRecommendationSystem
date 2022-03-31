package com.unipi.largescale.beans;

import com.unipi.largescale.entities.Comment;
import javafx.beans.property.SimpleStringProperty;

public class CommentBean {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty text;


    public CommentBean(String id, String name, String surname, String text){
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.text = new SimpleStringProperty(text);
    }

    public CommentBean(Comment comment){
        this.id = new SimpleStringProperty(comment.getId());
        this.name = new SimpleStringProperty(comment.getName());
        this.surname = new SimpleStringProperty(comment.getSurname());
        this.text = new SimpleStringProperty(comment.getText());
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public String getSurname() {
        return surname.get();
    }

    public String getText() {
        return text.get();
    }
}


