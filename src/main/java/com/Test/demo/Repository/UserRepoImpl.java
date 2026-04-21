package com.Test.demo.Repository;

import com.Test.demo.Constants.PlaceHolders;
import com.Test.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntity> getUserForSA(){
        Query query= new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<UserEntity> userEntities = mongoTemplate.find(query, UserEntity.class);
        return userEntities;


    }
}
