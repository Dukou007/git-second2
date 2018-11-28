package com.example.dao;

import com.example.model.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClazzDao extends JpaRepository<Clazz, Long> {
}
