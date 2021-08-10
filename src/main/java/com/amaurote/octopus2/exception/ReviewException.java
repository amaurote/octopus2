package com.amaurote.octopus2.exception;

public class ReviewException extends RuntimeException {
    public ReviewException(String message) {
        super("Review exception:" + message);
    }
}
