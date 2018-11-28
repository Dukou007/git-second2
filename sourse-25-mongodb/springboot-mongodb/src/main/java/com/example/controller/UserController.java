package com.example.controller;



import com.example.model.mongo.MongoUser;
import com.example.service.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    MongoUserService userService;

    @GetMapping("/user/add")
    public Object addUser() {
        MongoUser user = new MongoUser();
        user.setName("mongodb");
        user.setAddress("guangzhou");
        user.setAge(18);
        user.setUid(UUID.randomUUID().toString());

        userService.saveUser(user);
        return "添加成功";
    }

    @GetMapping("/user/update")
    public Object updateUser(){
        MongoUser user = userService.findUserByName("mongodb");
        user.setAge(28);
        userService.updateUser("mongodb", "age", "38");

        return "更新成功";

    }

    @GetMapping("/users")
    public Object listUsers() {
        return userService.listUser();
    }

    @GetMapping("/user/del")
    public Object delUser() {

        userService.removeUser("mongodb");
        return "删除成功";
    }

    /**
     * mysql数据库中的数据同步到mongo中
     * @return
     */
    @GetMapping("/user/allToMongo")
    public Object allToMongo(){
    	userService.allToMongo();
    	return "success";
    }

}
