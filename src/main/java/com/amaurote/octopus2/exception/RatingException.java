package com.amaurote.octopus2.exception;

public class RatingException extends RuntimeException {
    public RatingException(String message) {
        super("Rating error:" + message);
    }
}
