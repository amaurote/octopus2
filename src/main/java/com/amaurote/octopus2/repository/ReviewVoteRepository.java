package com.amaurote.octopus2.repository;

import com.amaurote.octopus2.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewVoteRepository extends JpaRepository<ReviewVote, Long> {

    ReviewVote findByAuthorAndReview(User author, Review review);

    int countByReviewAndVote(Review review, VoteType vote);
}
