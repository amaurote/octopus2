package com.amaurote.octopus2.exception;

public class RoleException extends RuntimeException {
    public RoleException(String message) {
        super("Role error: " + message);
    }
}
