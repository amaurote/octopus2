package com.amaurote.octopus2.service;

import com.amaurote.octopus2.domain.entity.Review;
import com.amaurote.octopus2.domain.entity.ReviewVote;
import com.amaurote.octopus2.domain.entity.User;
import com.amaurote.octopus2.domain.entity.VoteType;
import com.amaurote.octopus2.exception.ReviewVoteException;
import com.amaurote.octopus2.repository.ReviewRepository;
import com.amaurote.octopus2.repository.ReviewVoteRepository;
import com.amaurote.octopus2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewVoteService {

    private final ReviewVoteRepository reviewVoteRepository;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    public ReviewVoteService(ReviewVoteRepository reviewVoteRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewVoteRepository = reviewVoteRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public ReviewVote vote(Long reviewId, String username, VoteType vote) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewVoteException("Review not found."));
        User author = userRepository.findByUserName(username).orElseThrow(() -> new ReviewVoteException("User not found."));

        return vote(review, author, vote);
    }

    public ReviewVote vote(Review review, User author, VoteType vote) {
        ReviewVote voteObj = reviewVoteRepository.findByAuthorAndReview(author, review)
                .orElse(ReviewVote.builder()
                        .review(review)
                        .author(author)
                        .build());
        voteObj.setVote(vote);

        voteObj = reviewVoteRepository.save(voteObj);

        // recalculate votes of Review
        int upvotes = reviewVoteRepository.countByReviewAndVote(review, VoteType.UP);
        int downvotes = reviewVoteRepository.countByReviewAndVote(review, VoteType.DOWN);

        review.setUps(upvotes);
        review.setDowns(downvotes);
        review.setCount(upvotes + downvotes);
        review.setScore(upvotes - downvotes);

        reviewRepository.save(review);

        return voteObj;
    }
}
