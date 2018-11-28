package com.demo;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class MongoDb {

    public static void main(String[] args) {

        // 使用IP+端口号创建
        Mongo mongo = new Mongo("193.112.28.174", 27017);

        for (String name: mongo.getDatabaseNames()) {
            System.out.println(name);
        }

        DB db = mongo.getDB("admin");
        for(String name : db.getCollectionNames()) {

            System.out.println("collectionName: " + name);

        }
    }
}
