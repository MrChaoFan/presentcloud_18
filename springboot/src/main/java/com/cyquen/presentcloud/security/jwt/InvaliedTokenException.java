package com.cyquen.presentcloud.security.jwt;

public class InvaliedTokenException extends RuntimeException {

    public InvaliedTokenException(Throwable cause) {
        super(cause);
    }

    public InvaliedTokenException(String message) {
        super(message);
    }

    public InvaliedTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
