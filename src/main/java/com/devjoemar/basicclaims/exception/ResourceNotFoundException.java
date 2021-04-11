package com.devjoemar.basicclaims.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2848983782439195657L;

    public ResourceNotFoundException(final String message) {
        super(message);
    }

}
