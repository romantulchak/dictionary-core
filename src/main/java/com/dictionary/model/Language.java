package com.dictionary.model;

import com.dictionary.model.word.Word;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "language")
@Accessors(chain = true)
public class Language implements Comparable<Language> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(name = "name", unique = true)
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Column(name = "code", unique = true)
    @Size(min = 2, max = 2)
    private String code;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Word> words;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Language() {
    }

    public Language(String name, String code, UUID userId) {
        this.name = name;
        this.code = code;
        this.user = new User(userId);
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @Override
    public int compareTo(@NotNull Language language) {
        return Comparator.comparing(Language::getName)
                .thenComparing(Language::getCode)
                .compare(this, language);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return id == language.id && Objects.equals(name, language.name) && Objects.equals(code, language.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code);
    }
}
