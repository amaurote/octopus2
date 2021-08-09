package com.amaurote.octopus2.repository;

import lombok.Getter;

@Getter
public class ReviewVoteAggregateResults {
    private final int upvotes;
    private final int downvotes;

    public ReviewVoteAggregateResults(Integer upvotes, Integer downvotes) {
        this.upvotes = (upvotes == null) ? 0 : upvotes;
        this.downvotes = (downvotes == null) ? 0 : downvotes;
    }
}
