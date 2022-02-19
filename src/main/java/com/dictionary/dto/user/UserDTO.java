package com.dictionary.dto.user;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserDTO {

    private UUID id;

    @JsonView(View.UserView.class)
    private String firstName;

    @JsonView(View.UserView.class)
    private String lastName;

    @JsonView(View.UserView.class)
    private String email;

    @JsonView(View.UserView.class)
    private long totalCreatedWords;

    @JsonView(View.UserView.class)
    private long totalCreatedLanguages;

}
