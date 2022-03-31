package com.unipi.large.scale.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

abstract public class CustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;
}
