package com.amaurote.octopus2.repository;

import com.amaurote.octopus2.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    // find user by username or email
    @Query("select usr from User usr " +
            "where lower(usr.userName) = lower(:identity) " +
            "or (lower(usr.email) = lower(:identity) and usr.loginByEmail = true) ")
    Optional<User> findUser(String identity);
}
