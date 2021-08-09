package com.amaurote.octopus2.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GenericGenerator(name = "id", strategy = "com.amaurote.octopus2.domain.generator.IdLongGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @NotNull
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotNull
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    @NotNull
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    @Column(name = "active", nullable = false)
    @NotNull
    private boolean active;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private boolean enabled;

    @Column(name = "login_by_email", nullable = false, columnDefinition = "bit(1)")
    @NotNull
    private boolean loginByEmail;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
}
