package com.dictionary.model.word;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@Setter
public class Example {

    @Size(max = 100)
    private String example;

}
