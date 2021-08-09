package com.amaurote.octopus2.repository;


import com.amaurote.octopus2.domain.entity.Item;
import com.amaurote.octopus2.domain.entity.Rating;
import com.amaurote.octopus2.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("select new com.amaurote.octopus2.repository.RatingAggregateResults(" +
            " avg(r.score) as rating," +
            " count(r.score) as totalRatings)" +
            " from Rating r" +
            " where r.item =:item")
    RatingAggregateResults getRatingByItem(@Param("item") Item item);

    Optional<Rating> findByItemAndAuthor(Item item, User author);

}
