package com.amaurote.octopus2.service;

import com.amaurote.octopus2.domain.entity.Item;
import com.amaurote.octopus2.domain.entity.Rating;
import com.amaurote.octopus2.domain.entity.User;
import com.amaurote.octopus2.exception.RatingException;
import com.amaurote.octopus2.repository.ItemRepository;
import com.amaurote.octopus2.repository.RatingAggregateResults;
import com.amaurote.octopus2.repository.RatingRepository;
import com.amaurote.octopus2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    public RatingService(RatingRepository ratingRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Rating rateByCatalogId(String catalogId, String username, int rating) {
        User author = userRepository.findByUserName(username).orElseThrow(() -> new RatingException("User not found."));
        return rateOrUpdate(itemRepository.findOneByCatalogId(catalogId).orElse(null), author, rating);
    }

    public RatingAggregateResults getRatingByCatalogId(String catalogId) {
        Item item = itemRepository.findOneByCatalogId(catalogId).orElseThrow(() -> new RatingException("Item not found: " + catalogId));
        return getRatingByItem(item);
    }

    public RatingAggregateResults getRatingByItem(Item item) {
        return ratingRepository.getRatingByItem(item);
    }

    public Rating rateOrUpdate(Item item, User author, int score) {
        if (item == null || author == null)
            throw new RatingException("Unable to map rating");
        if (score <= 0 || score > 5)
            throw new RatingException("Invalid rating value");

        Rating rating = ratingRepository.findByItemAndAuthor(item, author)
                .orElse(Rating.builder()
                        .author(author)
                        .item(item)
                        .build());

        rating.setScore(score);

        return ratingRepository.save(rating);
    }

    public void removeRating(String catalogId, String username) {
        User author = userRepository.findByUserName(username).orElseThrow(() -> new RatingException("User not found."));
        Item item = itemRepository.findOneByCatalogId(catalogId).orElseThrow(() -> new RatingException("Item not found."));
        removeRating(item, author);
    }

    public void removeRating(Item item, User author) {
        ratingRepository.findByItemAndAuthor(item, author).ifPresent(ratingRepository::delete);
    }
}
