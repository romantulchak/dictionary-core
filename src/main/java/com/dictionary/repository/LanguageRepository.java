package com.dictionary.repository;

import com.dictionary.model.Language;
import com.dictionary.projection.LanguageWithUserIdProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    boolean existsByName(String name);

    Optional<Language> findByCode(String code);

    boolean existsByIdAndUserId(long id, UUID userId);

    @Query(value = "SELECT l.id as id, l.name as name, l.code as code, l.createAt as createAt, l.updateAt as updateAt, l.user.id as userId FROM Language l LEFT OUTER JOIN l.user")
    Slice<LanguageWithUserIdProjection> findAllWithUserId(Pageable pageable);

    Slice<Language> findAllByUserId(UUID userId, Pageable pageable);

    long countAllBy();

    long countLanguageByUserId(UUID id);
}
