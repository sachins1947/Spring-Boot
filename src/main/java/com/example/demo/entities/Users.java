package com.example.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
// It'll get the name of the collection automatically based on the class Name
@Data
@NoArgsConstructor
@Document
public class Users {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String name;
    private String password;
    @DBRef
    private List<JornalEntry> jornalEntries = new ArrayList<>();
    private List<String> roles;
}
