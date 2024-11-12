package com.example.demo.controller;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.JornalEntry;
import com.example.demo.entities.Users;
import com.example.demo.services.JournalEntryService;
import com.example.demo.services.UserEntryService;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/journal")
public class EntryController {
@Autowired
private JournalEntryService journalEntryService;
@Autowired
private UserEntryService userEntryService;
     @GetMapping
     public ResponseEntity<?> getJournalEntriesofUser() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String name = authentication.getName();
         Users user = userEntryService.findByUsername(name);
         List<JornalEntry> list =  user.getJornalEntries();
         if(list!=null && !list.isEmpty()){
             return new ResponseEntity<>(list,HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JornalEntry myentries) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            myentries.setDate(LocalTime.now());
            journalEntryService.saveEntry(myentries, name);
            return new ResponseEntity<>(myentries, HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("id/{id}")
     public  ResponseEntity<JornalEntry> getEntryByID(@PathVariable ObjectId id){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String name = authentication.getName();
         Users user = userEntryService.findByUsername(name);
         List<JornalEntry> collect = user.getJornalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
          if(!collect.isEmpty()){
         Optional<JornalEntry> journalentries= journalEntryService.getById(id);
         if(journalentries.isPresent()){
             return  new ResponseEntity<>(journalentries.get(), HttpStatus.OK);
         }
        }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
     @PutMapping("id/{id}")
     public ResponseEntity<?> updateEntry( @RequestBody JornalEntry newentries,@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Users user = userEntryService.findByUsername(name);
        List<JornalEntry> collect = user.getJornalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
          if(!collect.isEmpty()){
            Optional<JornalEntry> journalentries= journalEntryService.getById(id);
            if(journalentries.isPresent()){
                JornalEntry old = journalentries.get();
              old.setName(newentries.getName()!=null  ? newentries.getName() : old.getName());
              old.setDescription(newentries.getDescription()!=null  ? newentries.getDescription() : old.getDescription());
              journalEntryService.saveEntry(old);
              return new ResponseEntity<>(old,HttpStatus.OK);
          }
        }
       return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
     @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
      boolean removed =  journalEntryService.deletebyId(id,username);
     if(removed){
         return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }
     else{
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
     }

}
