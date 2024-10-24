package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Users;
import com.example.demo.services.UserEntryService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserEntryService userEntryService;
    @GetMapping
     public List<Users> getUsers() {
         return  userEntryService.getAll();
     }
     @PostMapping
     public boolean createUser(@RequestBody  Users user) {
         userEntryService.saveEntry(user);
         return true;
     }
     @PutMapping("/{name}")
     public ResponseEntity<?> updateUser(@RequestBody Users user , @PathVariable String name) {
         Users u =  userEntryService.findByUsername(name);
         if( u != null  ){
              u.setName(user.getName());
              u.setPassword(user.getPassword());
              userEntryService.saveEntry(u);
              return new ResponseEntity<>(u, HttpStatus.NO_CONTENT);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
}
