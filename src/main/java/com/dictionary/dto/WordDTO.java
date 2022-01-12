package com.dictionary.dto;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class WordDTO {

    @JsonView(View.WordView.class)
    private String name;

    @JsonView(View.WordView.class)
    private String description;
}
