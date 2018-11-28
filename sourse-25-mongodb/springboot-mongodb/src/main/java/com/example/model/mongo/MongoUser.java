package com.example.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection="users")
@CompoundIndexes({
    @CompoundIndex(name = "age_idx", def = "{'name': 1, 'age': -1}")
})
public class MongoUser implements Serializable {

	private String grade;//从mongo中读取数据的系统业务需要 但在数据库中并不存在于user表中或此列是由逻辑计算得来

	//user实体的属性
	@Id
	private String uid;

	private String name;
	private int age;

	@Transient
	private String address;

	@DBRef
	private MongoClazz clazz;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MongoClazz getClazz() {
		return clazz;
	}

	public void setClazz(MongoClazz clazz) {
		this.clazz = clazz;
	}
}
