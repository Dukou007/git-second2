package com.example;

import com.example.dao.StudentRepository;
import com.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;

@RestController
public class HelloController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/1")
    public Object test1() {

        return studentRepository.findByOrderByIdDesc();
    }

    @GetMapping("/2")
    public Object test2() {
        return studentRepository.findByNameStartingWithAndAgeLessThan("平", 19);
    }//分%

    @GetMapping("/3")
    public Object test3() {
        return studentRepository.findNameById(1L);
    }

    @GetMapping("/4")
    public void test4() {
        studentRepository.deleteByNameAndAge("tom", 11);
    }

    @GetMapping("/5")
    public Object test5() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Student> page = studentRepository.findByNameOrderByIdDesc(pageable,"tom");
        return page;
    }

    /**
     * root 查询的类型
     * query 查询条件
     * cb 构建Predicate
     * @return
     */
    @GetMapping("/6")
    public Object test6() {

        Sort sort = new Sort(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(0, 5, sort);

        Specification<Student> specification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("name");

                return criteriaBuilder.equal(path, "一明");
            }
        };

        Page<Student> page = studentRepository.findAll(specification, pageable);

        return page;

    }

}
