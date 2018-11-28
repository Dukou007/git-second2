package com.example.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "clazz")
public class MongoClazz implements Serializable {

    @Id
    private String cid;
    private String name;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MongoClazz() {
    }

    public MongoClazz(String cid, String name) {
        this.cid = cid;
        this.name = name;
    }
}
