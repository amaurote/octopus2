package com.amaurote.octopus2.dto;

import com.amaurote.octopus2.domain.entity.VoteType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
public class ReviewDTO {

    @NotNull
    @Size(min = 20, max = 1000)
    private String text;

    @Size(min = 2, max = 500)
    private String pros;

    @Size(min = 2, max = 500)
    private String cons;

    private Instant dateCreated;

    private Instant dateEdited;

    private Integer upVotes;

    private Integer downVotes;

    private VoteType userVote;

}
