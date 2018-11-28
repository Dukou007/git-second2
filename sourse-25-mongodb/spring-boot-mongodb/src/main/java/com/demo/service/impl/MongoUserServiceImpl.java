package com.demo.service.impl;

import java.util.List;
import java.util.UUID;

import com.demo.dao.ClazzDao;
import com.demo.model.Clazz;
import com.demo.model.mongo.MongoClazz;
import com.demo.model.mongo.MongoUser;
import com.demo.model.Users;
import com.demo.service.MongoUserService;
import com.demo.dao.UserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

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

            Clazz clazz = clazzDao.findOne(u.getClazzId());
            MongoClazz mongoClazz = new MongoClazz(clazz.getId().toString(), clazz.getName());
            //先插入关联对象
            mongoTemplate.save(mongoClazz);

            mongoUser.setClazz(mongoClazz);
            //插入最终实体
            mongoTemplate.save(mongoUser);
        }

    }
}
