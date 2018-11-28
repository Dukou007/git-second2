package com.demo.dao;

import com.demo.model.Clazz;
import com.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClazzDao extends JpaRepository<Clazz, Long> {
}
