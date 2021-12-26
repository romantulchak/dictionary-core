package com.dictionary.model;

import com.dictionary.model.type.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {

    @Id
    private long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Size(max = 255)
    private RoleType name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
