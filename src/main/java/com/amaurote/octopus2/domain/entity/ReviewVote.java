package com.amaurote.octopus2.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "review_votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    private Review review;

    @ManyToOne
    private User author;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vote", nullable = false)
    private VoteType vote;

}
