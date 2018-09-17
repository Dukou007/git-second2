package com.example.controller;


import com.example.entity.Student;
import com.example.service.StudentService;
import com.example.vo.Mess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Api(value = "学生控制器")
@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private StudentService studentService;

	@GetMapping("/rest")
	public Object restTest() {
		return restTemplate.getForObject("http://localhost:8080/students", Object.class);
	}

	@ApiOperation(value = "获取学生列表")
	@GetMapping("")
	public Object getStudents() {
        List<Student> students = studentService.selectList(null);
		return Mess.succ(students);
	}

	@ApiOperation("更新学生")
	@PostMapping("")
	public Object postStudent(@RequestBody Student student) {
		studentService.insert(student);
		return Mess.succ(null);
	}

	@GetMapping("/{id}")
	public Object getStudent(@PathVariable("id") Long id) {
        Student student = studentService.selectById(id);
		return Mess.succ(student);
	}

	@PutMapping("/{id}")
	public Object putStudent(@PathVariable("id") Long id, @RequestBody Student student) {
		Student stu = studentService.selectById(id);
		stu.setAge(student.getAge());
		stu.setName(student.getName());
		studentService.updateById(stu);

		return Mess.succ(null);
	}

	@DeleteMapping("/{id}")
	public Object deleteStudent(@PathVariable Long id) {
	    studentService.deleteById(id);
	    return Mess.succ(null);
    }
}

