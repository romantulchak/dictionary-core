package com.dictionary.dto.user;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleDTO {

    private UUID id;

    @JsonView(View.RoleView.class)
    private String name;

    public RoleDTO() {
    }
}

