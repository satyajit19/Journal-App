package com.Test.demo.Repository;

import com.Test.demo.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUsername(String Username);
    UserEntity deleteByUsername(String Username);
}
