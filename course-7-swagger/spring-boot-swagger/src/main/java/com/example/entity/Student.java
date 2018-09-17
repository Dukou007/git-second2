package com.example.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("学生")
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    private Integer age;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	@Override
	protected Serializable pkVal() {
		return this.id;
	}


    
}
