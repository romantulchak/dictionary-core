package com.dictionary.repository;

import com.dictionary.model.Role;
import com.dictionary.model.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType type);

    List<Role> findAllByUsersId(UUID id);
}
