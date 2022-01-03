package com.dictionary.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name = "word")
@Getter
@Setter
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(name = "name", unique = true)
    @Size(min = 1, max = 500)
    private String name;

    @NotBlank
    @Column(name = "capital_name", unique = true)
    @Size(min = 1, max = 500)
    private String capitalName;

    @NotBlank
    @Column(name = "lowercase_name", unique = true)
    @Size(min = 1, max = 500)
    private String lowercaseName;

    @NotBlank
    @Column(name = "uppercase_name", unique = true)
    @Size(min = 1, max = 500)
    private String uppercaseName;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    @NotBlank
    private String key;

    public Word(){}

    public Word(String name, User user, Language language, String key) {
        this.name = name;
        this.capitalName = StringUtils.capitalize(name);
        this.lowercaseName = name.toLowerCase(Locale.ROOT);
        this.uppercaseName = name.toUpperCase(Locale.ROOT);
        this.createAt = LocalDateTime.now();
        this.user = user;
        this.language = language;
        this.key = key;
    }
}
