package com.amaurote.octopus2.repository;

import com.amaurote.octopus2.domain.entity.Item;
import com.amaurote.octopus2.domain.entity.Review;
import com.amaurote.octopus2.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByItem(Item item, Pageable pageable);

    Optional<Review> findByItemAndAuthor(Item item , User author);

    Optional<Review> findById(Long id);
}
