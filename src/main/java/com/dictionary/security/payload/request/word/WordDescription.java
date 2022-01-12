package com.dictionary.security.payload.request.word;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WordDescription {

    @NotBlank
    private String word;

    private String description;
}
