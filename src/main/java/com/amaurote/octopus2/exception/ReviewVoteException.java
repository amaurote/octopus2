package com.amaurote.octopus2.exception;

public class ReviewVoteException extends RuntimeException {
    public ReviewVoteException(String message) {
        super("ReviewVote exception:" + message);
    }
}
