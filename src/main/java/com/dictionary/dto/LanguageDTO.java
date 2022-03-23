package com.dictionary.dto;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;

@Data
public class LanguageDTO implements Comparable<LanguageDTO> {

    @JsonView({View.LanguageView.class, View.LanguageWithPreferredView.class})
    private long id;

    @JsonView({View.LanguageView.class, View.LanguageWithPreferredView.class})
    private String name;

    @JsonView({View.LanguageView.class, View.LanguageWithPreferredView.class})
    private String code;

    @JsonView(View.LanguageView.class)
    private LocalDateTime createAt;

    @JsonView(View.LanguageView.class)
    private LocalDateTime updateAt;

    @JsonView(View.LanguageView.class)
    private PrivilegesDTO privileges;

    @JsonView(View.LanguageView.class)
    private boolean isPreferred;

    @Override
    public int compareTo(@NotNull LanguageDTO language) {
        return Comparator.comparing(LanguageDTO::getName)
                .thenComparing(LanguageDTO::getCode)
                .compare(this, language);
    }
}
