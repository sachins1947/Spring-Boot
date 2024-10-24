package com.example.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entities.Users;

public interface UserEntryRepository  extends MongoRepository<Users, ObjectId> {
   Users findByname(String username);
}
