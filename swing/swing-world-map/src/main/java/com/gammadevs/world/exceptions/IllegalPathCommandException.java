package com.gammadevs.world.exceptions;

@SuppressWarnings("serial")
public class IllegalPathCommandException extends Exception {

    public IllegalPathCommandException() {}

    public IllegalPathCommandException(String message) {
        super(message);
    }

    public IllegalPathCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
