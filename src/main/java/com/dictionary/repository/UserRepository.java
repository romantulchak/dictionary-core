package com.dictionary.repository;

import com.dictionary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query(value = "SELECT u.preferredLanguage.id as preferredLanguage FROM User u WHERE u.username = ?1")
    Long findUserPreferredLanguageId(String username);
}
