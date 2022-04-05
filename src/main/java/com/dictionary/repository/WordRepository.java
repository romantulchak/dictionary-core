package com.dictionary.repository;

import com.dictionary.model.word.Word;
import com.dictionary.projection.WordKeyProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, Long> {

    boolean existsByNameAndLanguageCode(String word, String code);

    Optional<WordKeyProjection> findByCapitalNameAndLanguageCode(String name, String code);

    @Query(value = "SELECT w FROM Word w WHERE w.capitalName = ?1 AND w.language.code = ?2")
    Optional<Word> findWordKeys(String name, String code);

    @Query(value = "SELECT w FROM Word w LEFT OUTER JOIN w.keys wk WHERE wk = ?1 OR w.key = ?1 AND w.language.code = ?2")
    List<Word> findByKeysInAndLanguageCode(String key, String code);

    long countWordByUserId(UUID id);

    Slice<Word> findAllByUserId(UUID id, Pageable pageable);

    Slice<Word> findWordByNameStartsWithAndUserId(String letter, UUID id, Pageable pageable);
}
