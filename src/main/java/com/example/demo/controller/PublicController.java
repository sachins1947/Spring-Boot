package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.Users;
import com.example.demo.services.UserEntryService;
@RestController
@RequestMapping("/public")
public class PublicController {
 @Autowired
    UserEntryService userEntryService;
     @PostMapping("create-user")
     public boolean createUser(@RequestBody  Users user) {
         userEntryService.save_new_user(user);
         return true;
     }
}
