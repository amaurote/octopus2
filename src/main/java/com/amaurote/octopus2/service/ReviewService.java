package com.amaurote.octopus2.service;

import com.amaurote.octopus2.domain.entity.*;
import com.amaurote.octopus2.dto.PageWrapper;
import com.amaurote.octopus2.dto.ReviewDTO;
import com.amaurote.octopus2.exception.ReviewException;
import com.amaurote.octopus2.repository.ItemRepository;
import com.amaurote.octopus2.repository.ReviewRepository;
import com.amaurote.octopus2.repository.ReviewVoteRepository;
import com.amaurote.octopus2.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewVoteRepository reviewVoteRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewVoteRepository reviewVoteRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewVoteRepository = reviewVoteRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ReviewDTO saveOrUpdate(ReviewDTO reviewDTO, String catalogId, String username) {
        Item item = itemRepository.findOneByCatalogId(catalogId).orElseThrow(() -> new ReviewException("Item not found: " + catalogId));
        User currentUser = userRepository.findByUserName(username).orElseThrow(() -> new ReviewException("User not found: " + catalogId));
        return saveOrUpdate(reviewDTO, item, currentUser);
    }

    public ReviewDTO saveOrUpdate(ReviewDTO reviewDTO, Item item, User author) {
        var review = reviewRepository.findByItemAndAuthor(item, author)
                .orElse(Review.builder()
                        .item(item)
                        .author(author)
                        .text(reviewDTO.getText())
                        .pros(reviewDTO.getPros())
                        .cons(reviewDTO.getCons())
                        .build()
                );

        if (review.getDateCreated() == null) {
            review.setDateCreated(Instant.now());
        } else {
            review.setDateEdited(Instant.now());
        }

        return reviewToLightDTO(reviewRepository.save(review));
    }

    @Transactional
    public PageWrapper<ReviewDTO> getAllReviews(String catalogId, String currentUserName, int page, int pageSize) {
        Item item = itemRepository.findOneByCatalogId(catalogId).orElseThrow(() -> new ReviewException("Item not found: " + catalogId));
        User currentUser = userRepository.findByUserName(currentUserName).orElse(null);

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
                .id(review.getId())
                .text(review.getText())
                .pros(review.getPros())
                .cons(review.getCons())
                .dateCreated(review.getDateCreated())
                .dateEdited(review.getDateEdited())
                .build();
    }

    private ReviewDTO reviewToDTO(Review review, User author) {
        ReviewVote userVote = reviewVoteRepository.findByAuthorAndReview(author, review).orElse(null);

        return ReviewDTO.builder()
                .id(review.getId())
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
