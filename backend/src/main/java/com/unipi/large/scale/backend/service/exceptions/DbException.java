package com.unipi.large.scale.backend.service.exceptions;

public class DbException extends RuntimeException{

    public DbException() {
        super("Entity not found!");
    }
    public DbException(String message) {
        super(message);
    }
}
