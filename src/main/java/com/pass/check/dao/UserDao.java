package com.pass.check.dao;

import com.pass.check.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(final String username);
}
