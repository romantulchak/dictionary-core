package com.dictionary.dto;

import com.dictionary.model.View;
import com.fasterxml.jackson.annotation.JsonView;

public class PrivilegesDTO {

    @JsonView(View.LanguageView.class)
    private boolean canModify;

    @JsonView(View.LanguageView.class)
    private boolean canDelete;

    public PrivilegesDTO(boolean canModify, boolean canDelete) {
        this.canModify = canModify;
        this.canDelete = canDelete;
    }
}
