package com.amaurote.octopus2.repository;

import com.amaurote.octopus2.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewVoteRepository extends JpaRepository<ReviewVote, Long> {

    Optional<ReviewVote> findByAuthorAndReview(User author, Review review);

    int countByReviewAndVote(Review review, VoteType vote);
}
