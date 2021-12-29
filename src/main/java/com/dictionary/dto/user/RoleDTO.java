package com.dictionary.dto.user;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class RoleDTO {

    private long id;

    @JsonView(View.RoleView.class)
    private String name;

    public RoleDTO() {
    }
}

