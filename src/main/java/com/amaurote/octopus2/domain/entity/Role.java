package com.amaurote.octopus2.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auth_roles")
// do not use @Data with ManyToMany relationship (StackOverflow exception)
@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    @NotNull
    private Integer id;

    @Column(name = "role_name", nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(name = "role_desc")
    private String desc;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
