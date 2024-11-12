package com.example.demo.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.JornalEntry;
import com.example.demo.entities.Users;
import com.example.demo.repository.JournalEntryRepository;

import java.util.List;
import java.util.Optional;
@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;
    @Transactional
     public  void saveEntry(JornalEntry jornalEntry,String name){
        
              Users user = userEntryService.findByUsername(name);
              JornalEntry save = journalEntryRepository.save(jornalEntry);
              user.getJornalEntries().add(save);
              userEntryService.save(user);

     }
    public  void saveEntry(JornalEntry jornalEntry){
         journalEntryRepository.save(jornalEntry);
    }
     public List<JornalEntry> getAll(){
         return journalEntryRepository.findAll();
     }
     public Optional<JornalEntry> getById(ObjectId id){
       return    journalEntryRepository.findById(id);
     }
     @Transactional
     public  boolean deletebyId(ObjectId id, String username){
        boolean removed =  false;
        try {
            Users user = userEntryService.findByUsername(username);
          removed  =  user.getJornalEntries().removeIf(entry -> entry.getId().equals(id));
          if(removed){
          userEntryService.save(user);
          journalEntryRepository.deleteById(id);
          }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occur while deleting the error ",e);
        }
         return removed;
     }


}
