package com.example.demo.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.LocalTime;
@Data
@Document(collection = "journal_entries")
public class JornalEntry {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private LocalTime date;  
}
