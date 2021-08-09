package com.amaurote.octopus2.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.octopus2.domain.generator.IdLongGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @NotNull
    private Long id;

    @Column(name = "cat_id", nullable = false, unique = true)
    @NotNull
    private String catalogId;

    @Column(name = "item_name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_added", updatable = false, nullable = false)
    @NotNull
    private Instant dateAdded;


}
