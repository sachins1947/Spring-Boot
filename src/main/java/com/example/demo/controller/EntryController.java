package com.example.demo.controller;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.JornalEntry;
import com.example.demo.entities.Users;
import com.example.demo.services.JournalEntryService;
import com.example.demo.services.UserEntryService;

import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class EntryController {
@Autowired
private JournalEntryService journalEntryService;
@Autowired
private UserEntryService userEntryService;
     @GetMapping("{name}")
     public ResponseEntity<?> getJournalEntriesofUser(@PathVariable String name) {
         Users user = userEntryService.findByUsername(name);
         List<JornalEntry> list =   user.getJornalEntries();
         if(list!=null && !list.isEmpty()){
             return new ResponseEntity<>(list,HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    @PostMapping("{name}")
    public ResponseEntity<?> createEntry(@RequestBody JornalEntry myentries, @PathVariable String name) {
        try {
            myentries.setDate(LocalTime.now());
            journalEntryService.saveEntry(myentries, name);
            return new ResponseEntity<>(myentries, HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("id/{id}")
     public  ResponseEntity<JornalEntry> getEntryByID(@PathVariable ObjectId id){
         Optional<JornalEntry> journalentries=    journalEntryService.getById(id);
         if(journalentries.isPresent()){
             return  new ResponseEntity<>(journalentries.get(), HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
     @PutMapping("{username}/{id}")
     public ResponseEntity<?> updateEntry( @RequestBody JornalEntry newentries,@PathVariable ObjectId id) {
          JornalEntry old= journalEntryService.getById(id).orElse(null);
          if(old!=null){
              old.setName(newentries.getName()!=null  ? newentries.getName() : old.getName());
              old.setDescription(newentries.getDescription()!=null  ? newentries.getDescription() : old.getDescription());
              journalEntryService.saveEntry(old);
              return new ResponseEntity<>(old,HttpStatus.OK);
          }
       return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
     @DeleteMapping("{username}/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id,@PathVariable String username) {
         journalEntryService.deletebyId(id,username);
          return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

}
