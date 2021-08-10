package com.amaurote.octopus2.controller;

import com.amaurote.octopus2.domain.entity.VoteType;
import com.amaurote.octopus2.dto.ReviewDTO;
import com.amaurote.octopus2.exception.ReviewException;
import com.amaurote.octopus2.exception.ReviewVoteException;
import com.amaurote.octopus2.service.ReviewService;
import com.amaurote.octopus2.service.ReviewVoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Locale;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewVoteService reviewVoteService;

    public ReviewController(ReviewService reviewService, ReviewVoteService reviewVoteService) {
        this.reviewService = reviewService;
        this.reviewVoteService = reviewVoteService;
    }

    @PostMapping("/review")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> postReview(@RequestBody ReviewDTO dto,
                                        @RequestParam(name = "catid") @NotNull @NotBlank String catalogId,
                                        Principal principal) {
        if(principal == null)
            throw new ReviewException("User error.");

        var newReview = reviewService.saveOrUpdate(dto, catalogId, principal.getName());
        return new ResponseEntity<>(newReview, HttpStatus.OK);
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReviews(@RequestParam(name = "catid") @NotNull @NotBlank String catalogId,
                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(name = "pagesize", required = false, defaultValue = "5") int pageSize,
                                           Principal principal) {

        var response = reviewService.getAllReviews(catalogId, (principal != null) ? principal.getName() : "", page, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/vote")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> voteForReview(@RequestParam(name = "review_id") @NotNull Long reviewId,
                                           @RequestParam(name = "vote") @NotNull String voteStr,
                                           Principal principal) {
        if(principal == null)
            throw new ReviewException("User error.");

        VoteType vote;
        switch (voteStr.toLowerCase(Locale.ROOT)) {
            case "up":
                vote = VoteType.UP;
                break;
            case "down":
                vote = VoteType.DOWN;
                break;
            default: throw new ReviewVoteException("Invalid vote.");
        }

        reviewVoteService.vote(reviewId, principal.getName(), vote);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> removeVote() {
        // TODO
        return null;
    }

}
