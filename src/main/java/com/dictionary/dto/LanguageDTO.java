package com.dictionary.dto;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LanguageDTO {

    @JsonView(View.LanguageView.class)
    private long id;

    @JsonView(View.LanguageView.class)
    private String name;

    @JsonView(View.LanguageView.class)
    private String code;

    @JsonView(View.LanguageView.class)
    private LocalDateTime createAt;

    @JsonView(View.LanguageView.class)
    private LocalDateTime updateAt;

    @JsonView(View.LanguageView.class)
    private PrivilegesDTO privileges;

}
