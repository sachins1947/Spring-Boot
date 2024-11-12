package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Users;
import com.example.demo.services.UserEntryService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserEntryService userEntryService;
    //  @PutMapping("/update-user")
    //  public ResponseEntity<?> updateUser(@RequestBody Users user) {
    //    org.springframework.security.core.Authentication authentication =   SecurityContextHolder.getContext().getAuthentication(); // it is used to stored the authenticated data ;
    //    String name   = authentication.getName();
    //    Users u =  userEntryService.findByUsername(name);
    //           u.setName(user.getName());
    //           u.setPassword(user.getPassword());
    //           userEntryService.saveEntry(u);
    //           return new ResponseEntity<>(u, HttpStatus.NO_CONTENT);
         
         
    //  }
    @PutMapping("/update-user")
public ResponseEntity<?> updateUser(@RequestBody Users user) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    System.out.println(name);
    Users u = userEntryService.findByUsername(name);

    if (u == null) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    try {
        u.setName(user.getName());
        u.setPassword(user.getPassword()); // Make sure to encode the password
        userEntryService.save_new_user(u);
        return new ResponseEntity<>(u, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
        return new ResponseEntity<>("Error updating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
