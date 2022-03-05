package com.dictionary.security.payload.request.word;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class WordDescription {

    @NotBlank
    private String word;

    private String description;

    private String source;

    private List<String> examples;
}
