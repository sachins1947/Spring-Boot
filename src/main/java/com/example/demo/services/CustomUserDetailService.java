package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Users;
import com.example.demo.repository.UserEntryRepository;
@Service
@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserEntryRepository userRepository;
   

   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
       Users user = userRepository.findByname(username);
       if(user!=null){
        System.out.println("User found: " + user.getName());
       return org.springframework.security.core.userdetails.User.builder()
        .username(user.getName())
        .password(user.getPassword())
        .roles(user.getRoles().toArray(new String[0]))
        .build(); 
       }
       throw new UsernameNotFoundException(username);
   }
}
