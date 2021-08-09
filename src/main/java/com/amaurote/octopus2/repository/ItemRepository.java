package com.amaurote.octopus2.repository;

import com.amaurote.octopus2.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findOneByCatalogId(String catalogId);

    @Query("select item from Item item " +
            "where lower(item.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Item> search(@Param("searchTerm") String searchTerm);

}
