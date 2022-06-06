package com.pass.check.dao;

import com.pass.check.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {
    Optional<Role> findByRoleName(String role_name);
}
