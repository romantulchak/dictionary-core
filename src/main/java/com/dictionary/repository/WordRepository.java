package com.dictionary.repository;

import com.dictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {

    boolean existsByNameAndLanguageCode(String name, String code);
}
