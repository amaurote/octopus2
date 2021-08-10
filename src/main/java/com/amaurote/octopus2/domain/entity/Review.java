package com.amaurote.octopus2.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.octopus2.domain.generator.IdLongGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Item item;

    @Column(name = "text", length = 1000, nullable = false)
    private String text;

    @Column(name = "pros", length = 500)
    private String pros;

    @Column(name = "cons", length = 500)
    private String cons;

    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_edited")
    private Instant dateEdited;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewVote> votes = new ArrayList<>();

    // redundancy for easier sorting
    @Column
    private Integer ups;

    @Column
    private Integer downs;

    @Column
    private Integer count;

    @Column
    private Integer score;
}
