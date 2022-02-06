package com.dictionary.model.word;

import com.dictionary.model.Language;
import com.dictionary.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
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
    @Column(name = "key")
    private String key;

    @ElementCollection
    @CollectionTable(name = "word_keys", joinColumns = @JoinColumn(name = "word_id"))
    @Column(name = "key")
    private List<String> keys;

    @Size(min = 3, max = 500)
    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "word_examples", joinColumns = @JoinColumn(name = "word_id"))
    @Column(name = "example")
    private List<Example> examples;

    @Size(min = 1, max = 100)
    private String pronunciation;

    public Word(){}

    public Word(String name, User user, Language language, String key, String description){
        this.name = name;
        this.capitalName = StringUtils.capitalize(name);
        this.lowercaseName = name.toLowerCase(Locale.ROOT);
        this.uppercaseName = name.toUpperCase(Locale.ROOT);
        this.createAt = LocalDateTime.now();
        this.user = user;
        this.language = language;
        this.key = key;
        this.description = description;
    }
}
