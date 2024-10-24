package com.example.demo.repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.JornalEntry;

@Repository
public interface JournalEntryRepository  extends MongoRepository<JornalEntry, ObjectId> {
}
