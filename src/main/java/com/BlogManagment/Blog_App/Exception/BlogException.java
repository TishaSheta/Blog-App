package com.BlogManagment.Blog_App.Exception;

import org.springframework.http.HttpStatus;

public class BlogException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    public BlogException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = (httpStatus != null) ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    }

