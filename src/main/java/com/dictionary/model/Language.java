package com.dictionary.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "language")
@Accessors(chain = true)
public class Language {

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

    @OneToMany(mappedBy = "language")
    private List<Word> words;

    public Language(){}

    public Language(String name, String code) {
        this.name = name;
        this.code = code;
        this.createAt = LocalDateTime.now();
    }
}
