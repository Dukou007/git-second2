package com.example.service.impl;


import com.example.dao.ClazzDao;
import com.example.dao.UserDao;
import com.example.model.Clazz;
import com.example.model.Users;
import com.example.model.mongo.MongoClazz;
import com.example.model.mongo.MongoUser;
import com.example.service.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("userService")
public class MongoUserServiceImpl implements MongoUserService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserDao userDao;

    @Autowired
    ClazzDao clazzDao;

    public void saveUser(MongoUser users) {
        mongoTemplate.save(users);
    }

    public MongoUser findUserByName(String name) {

        return mongoTemplate.findOne(
                new Query(Criteria.where("name").is(name)), MongoUser.class);
    }

    public void removeUser(String name) {
        mongoTemplate.remove(new Query(Criteria.where("name").is(name)),
                MongoUser.class);
    }

    public void updateUser(String name, String key, String value) {
        mongoTemplate.updateFirst(new Query(Criteria.where("name").is(name)),
                Update.update(key, value), MongoUser.class);

    }

    public List<MongoUser> listUser() {
        return mongoTemplate.findAll(MongoUser.class);
    }

    @Override
    public void allToMongo() {

        List<Users> users = userDao.findAll();

        for (Users u : users) {
            MongoUser mongoUser = new MongoUser();
            mongoUser.setAddress(u.getAddress());
            mongoUser.setAge(u.getAge());
            mongoUser.setName(u.getName());
            mongoUser.setUid(UUID.randomUUID().toString());

            Clazz clazz = clazzDao.findById(u.getClazzId()).get();
            MongoClazz mongoClazz = new MongoClazz(clazz.getId().toString(), clazz.getName());
            //先插入关联对象
            mongoTemplate.save(mongoClazz);

            mongoUser.setClazz(mongoClazz);
            //插入最终实体
            mongoTemplate.save(mongoUser);
        }

    }
}
