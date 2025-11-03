package com.kunal.journalApp.controller;

import com.kunal.journalApp.entity.JournalEntry;
import com.kunal.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry newEntry) {
        newEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(newEntry);
        return true;
    }

    @GetMapping("id/{journalEntryId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId journalEntryId){
        return journalEntryService.findById(journalEntryId).orElse(null);
    }

    @DeleteMapping("id/{journalEntryId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId journalEntryId){
        journalEntryService.deleteById(journalEntryId);
        return true;
    }

    @PutMapping("id/{journalEntryId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId journalEntryId, @RequestBody JournalEntry updatedEntry){
        JournalEntry oldJournalEntry = journalEntryService.findById(journalEntryId).orElse(null);
        if(oldJournalEntry != null){
            oldJournalEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldJournalEntry.getContent());
        }
        journalEntryService.saveEntry(oldJournalEntry);
        return oldJournalEntry;
    }

}
