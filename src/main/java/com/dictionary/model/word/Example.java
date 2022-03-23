package com.dictionary.model.word;

import com.ecfinder.core.anotation.ECF;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@ECF(tableName = "examples")
@Embeddable
@Getter
@Setter
public class Example {

    @Size(max = 100)
    private String example;

}
