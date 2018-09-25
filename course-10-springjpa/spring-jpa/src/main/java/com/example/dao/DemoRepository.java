package com.example.dao;


import com.example.entity.Student;
import org.springframework.data.repository.Repository;

public interface DemoRepository extends Repository<Student, Long> {
}
