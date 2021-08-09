package com.amaurote.octopus2.dto;

import com.amaurote.octopus2.repository.RatingAggregateResults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private String catalogId;
    private String name;
    private String description;
    private Instant dateAdded;

    private RatingAggregateResults rating;

}
