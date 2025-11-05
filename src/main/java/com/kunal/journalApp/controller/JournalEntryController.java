package com.kunal.journalApp.controller;

import com.kunal.journalApp.entity.JournalEntry;
import com.kunal.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all= journalEntryService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(journalEntryService.getAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry) {
        try {
            newEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{journalEntryId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalEntryId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(journalEntryId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{journalEntryId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId journalEntryId){
        journalEntryService.deleteById(journalEntryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{journalEntryId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId journalEntryId, @RequestBody JournalEntry updatedEntry){
        JournalEntry oldJournalEntry = journalEntryService.findById(journalEntryId).orElse(null);
        if(oldJournalEntry != null){
            oldJournalEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldJournalEntry.getContent());
            journalEntryService.saveEntry(oldJournalEntry);
            return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
