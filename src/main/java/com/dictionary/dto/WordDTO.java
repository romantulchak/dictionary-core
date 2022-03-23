package com.dictionary.dto;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class WordDTO {

    @JsonView(View.WordView.class)
    private long id;

    @JsonView(View.WordView.class)
    private String name;

    @JsonView(View.WordView.class)
    private String description;

    @JsonView(View.WordView.class)
    private String pronunciation;

    @JsonView(View.WordDetailsView.class)
    private List<String> examples;

}
