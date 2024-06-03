package com.security.basic.repository;

import com.security.basic.entity.EnumRole;
import com.security.basic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(EnumRole authority);
}
