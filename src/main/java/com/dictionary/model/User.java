package com.dictionary.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 25)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 30)
    private String lastName;

    @NotBlank
    @Column(name = "username", nullable = false)
    @Size(min = 3, max = 15)
    private String username;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false)
    @Size(min = 3, max = 40)
    private String email;

    @NotBlank
    @Column(name = "password", nullable = false, unique = true)
    @Size(min = 4, max = 90)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Word> words;

    @OneToMany(mappedBy = "user")
    private List<Language> languages;
}
