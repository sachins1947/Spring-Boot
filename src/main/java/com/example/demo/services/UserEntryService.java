package com.example.demo.services;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Users;
import com.example.demo.repository.UserEntryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;
    private static final PasswordEncoder password = new BCryptPasswordEncoder();
    public  void save_new_user(Users userentry){
        userentry.setPassword(password.encode(userentry.getPassword()));
        userentry.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(userentry);
    }
    public void save(Users user){
        userEntryRepository.save(user);
    }
    public List<Users> getAll(){
        return userEntryRepository.findAll();
    }
    public Optional<Users> getById(ObjectId id){
        return    userEntryRepository.findById(id);
    }
    public  void deletebyId(ObjectId id){
        userEntryRepository.deleteById(id);
    }
    public  Users findByUsername(String username){
        return userEntryRepository.findByname(username);
    }
}
