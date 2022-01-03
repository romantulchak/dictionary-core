package com.dictionary.security.payload.request.word;

import lombok.Data;

import java.util.List;

@Data
public class CreateWordRequest {

    private String name;

    private String code;

    private String word;

    List<CreateWordRequest> languagesTo;

}
