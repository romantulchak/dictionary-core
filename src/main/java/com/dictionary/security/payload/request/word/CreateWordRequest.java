package com.dictionary.security.payload.request.word;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
public class CreateWordRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    Set<String> words;

    List<CreateWordRequest> languagesTo;

}
