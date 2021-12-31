package com.dictionary.security.payload.request.word;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateWordRequest {

    @NotBlank
    @Size(min = 2, max = 2)
    private String code;

    @Size(min = 1, max = 500)
    private String name;

}
