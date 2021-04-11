package com.devjoemar.basicclaims.exception;

public class ClaimProcessException  extends RuntimeException {
    private static final long serialVersionUID = -2718074199905650698L;

    public ClaimProcessException(final String message) {
        super(message);
    }
}

