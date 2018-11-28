package com.demo.dao;

import com.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<Users, Long> {




}
