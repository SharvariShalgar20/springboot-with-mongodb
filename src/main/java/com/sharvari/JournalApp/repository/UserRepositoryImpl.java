package com.sharvari.JournalApp.repository;

import com.sharvari.JournalApp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Users> findUsersWithEmail() {
        // Criteria: email field exists AND is not empty
        Criteria criteria = Criteria.where("email")
                .exists(true)
                .ne(null)
                .ne("");

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Users.class);
    }
}
