package com.dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class JwtDTO {

    private UUID id;

    private String token;

    private String username;

    private String email;
}
