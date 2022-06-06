package com.pass.check.dao;

import com.pass.check.entity.Pass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PassCheckerDao extends CrudRepository<Pass, Integer> {
}
