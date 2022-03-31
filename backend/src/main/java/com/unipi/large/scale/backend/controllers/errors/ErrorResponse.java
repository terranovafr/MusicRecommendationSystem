package com.unipi.large.scale.backend.controllers.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    @JsonProperty("error")
    ErrorBody errorBody;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.errorBody = new ErrorBody(httpStatus.toString(), message);
    }

    public ErrorBody getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(ErrorBody errorBody) {
        this.errorBody = errorBody;
    }
}
