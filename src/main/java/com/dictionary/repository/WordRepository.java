package com.dictionary.repository;

import com.dictionary.model.Word;
import com.dictionary.projection.WordKeyProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    boolean existsByNameAndLanguageCode(String word, String code);

    Optional<WordKeyProjection> findByCapitalNameAndLanguageCode(String name, String code);

    List<Word> findByKeyAndLanguageCode(String key, String code);
}
