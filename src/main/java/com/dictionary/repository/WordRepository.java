package com.dictionary.repository;

import com.dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    boolean existsByNameAndLanguageCode(String word, String code);

    Optional<Word> findByCapitalNameAndLanguageCode(String name, String code);

    @Query(value = "SELECT w FROM Word w LEFT OUTER JOIN w.keys wk WHERE wk = ?1 AND w.language.code = ?2")
    List<Word> findByKeysInAndLanguageCode(String key, String code);

}
