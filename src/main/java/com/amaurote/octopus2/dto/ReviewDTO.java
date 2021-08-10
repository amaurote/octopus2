package com.amaurote.octopus2.dto;

import com.amaurote.octopus2.domain.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private long id;

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
