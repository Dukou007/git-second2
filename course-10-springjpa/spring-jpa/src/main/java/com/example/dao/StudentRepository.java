package com.example.dao;



import com.example.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    public List<Student> findByOrderByIdDesc();

    Student findByNameStartingWithAndAgeLessThan(String name, int i);

    @Query("select s.name from Student s where s.id = ?1")
    String findNameById(Long id);

    @Modifying
    @Transactional
    @Query("delete from Student where name = ?1 and age = ?2")
    void deleteByNameAndAge(String name, int age);

    Page<Student> findByNameOrderByIdDesc(Pageable pageable, String name);

}
