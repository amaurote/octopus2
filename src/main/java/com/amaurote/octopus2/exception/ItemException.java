package com.amaurote.octopus2.exception;

public class ItemException extends RuntimeException {
    public ItemException(String message) {
        super("Item error:" + message);
    }
}
