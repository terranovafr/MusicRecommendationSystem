package com.unipi.large.scale.backend.service.exceptions;

public class RegistrationException extends RuntimeException {

    public RegistrationException() {
        super("There was an error during registration");
    }
    public RegistrationException(String message) {
        super(message);
    }

}
