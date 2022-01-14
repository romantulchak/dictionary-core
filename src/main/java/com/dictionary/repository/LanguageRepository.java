package com.dictionary.repository;

import com.dictionary.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    boolean existsByName(String name);

    Optional<Language> findByCode(String code);

    boolean existsByIdAndUserId(long id, UUID userId);
}
