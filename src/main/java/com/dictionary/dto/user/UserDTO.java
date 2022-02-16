package com.dictionary.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private long totalCreatedWords;

    private long totalCreatedLanguages;

}
