package com.dictionary.security.payload.request.word;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CreateWordRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    List<String> words;

    List<CreateWordRequest> languagesTo;

}
