package com.amaurote.octopus2.service;

import com.amaurote.octopus2.domain.entity.*;
import com.amaurote.octopus2.dto.PageWrapper;
import com.amaurote.octopus2.dto.ReviewDTO;
import com.amaurote.octopus2.repository.ReviewRepository;
import com.amaurote.octopus2.repository.ReviewVoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewVoteRepository reviewVoteRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewVoteRepository reviewVoteRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewVoteRepository = reviewVoteRepository;
    }

    public ReviewDTO saveOrUpdate(ReviewDTO reviewDTO, Item item, User author) {
        var review = reviewRepository.findByItemAndAuthor(item, author).orElse(new Review());



        return null;
    }

    @Transactional
    public PageWrapper<ReviewDTO> getAllReviews(Item item, User currentUser, int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize, Sort.Direction.ASC, "dateCreated");

        List<ReviewDTO> elements = new ArrayList<>();
        PageWrapper<ReviewDTO> pageWrapper = new PageWrapper<>();

        Page<Review> reviewPage = reviewRepository.findByItem(item, pageRequest);

        for (Review r : reviewPage.getContent())
            elements.add(reviewToDTO(r, currentUser));

        pageWrapper.setTotalPages(reviewPage.getTotalPages());
        pageWrapper.setTotalCount(reviewPage.getTotalElements());
        pageWrapper.setElements(elements);

        return pageWrapper;
    }

    private ReviewDTO reviewToLightDTO(Review review) {
        return ReviewDTO.builder()
                .text(review.getText())
                .pros(review.getPros())
                .cons(review.getCons())
                .dateCreated(review.getDateCreated())
                .dateEdited(review.getDateEdited())
                .build();
    }

    private ReviewDTO reviewToDTO(Review review, User author) {
        ReviewVote userVote = reviewVoteRepository.findByAuthorAndReview(author, review);

        return ReviewDTO.builder()
                .text(review.getText())
                .pros(review.getPros())
                .cons(review.getCons())
                .dateCreated(review.getDateCreated())
                .dateEdited(review.getDateEdited())
                .upVotes(reviewVoteRepository.countByReviewAndVote(review, VoteType.UP))
                .downVotes(reviewVoteRepository.countByReviewAndVote(review, VoteType.DOWN))
                .userVote((userVote != null) ? userVote.getVote() : null)
                .build();
    }
}
