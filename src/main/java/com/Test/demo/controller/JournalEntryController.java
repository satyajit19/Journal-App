package com.Test.demo.controller;

import com.Test.demo.dto.JournalEntryDto;
import com.Test.demo.entity.JournalEntry;
import com.Test.demo.entity.UserEntity;
import com.Test.demo.services.JournalEntryServices;
import com.Test.demo.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryServices journalEntryService;
    @Autowired
    private UserServices userServices;

    @GetMapping()
    public ResponseEntity<?> getAllEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userServices.findByUsername(username);
        List<JournalEntry> all = user.getJournal_entries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all.stream().map(this::toDto).collect(Collectors.toList()), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            if (myEntry.getTitle() == null || myEntry.getTitle().isBlank()) {
                return new ResponseEntity<>("Title is required", HttpStatus.BAD_REQUEST);
            }
            if (myEntry.getContent() == null || myEntry.getContent().isBlank()) {
                return new ResponseEntity<>("Content is required", HttpStatus.BAD_REQUEST);
            }
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(toDto(myEntry),HttpStatus.CREATED);
        }catch(Exception e){
            log.error("Failed to create journal entry for user {}", username, e);
            return new ResponseEntity<>("Failed to save journal entry: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }



    }

    @GetMapping("id/{myid}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userServices.findByUsername(username);
        ObjectId objectId = parseObjectId(myid);
        Optional<JournalEntry> entry = user.getJournal_entries().stream().filter(x -> x.getMyid().equals(objectId)).findFirst();
        return entry.<ResponseEntity<?>>map(journalEntry -> new ResponseEntity<>(toDto(journalEntry), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String myid ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userServices.findByUsername(username);
        ObjectId objectId = parseObjectId(myid);
        boolean boll = user.getJournal_entries().removeIf(x -> x.getMyid().equals(objectId));
        if(boll){
            userServices.saveEntry(user);
            journalEntryService.deleteById(objectId);
            return new ResponseEntity<>("Journal entry deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("id/{myid}")
    public ResponseEntity<?>  updateJournalEntryById(
            @PathVariable String myid,
            @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userServices.findByUsername(username);
        ObjectId objectId = parseObjectId(myid);
        Optional<JournalEntry> Entry = user.getJournal_entries().stream().filter(x -> x.getMyid().equals(objectId)).findFirst();
        if(Entry.isPresent()){
            JournalEntry oldEntry = Entry.get();
            oldEntry.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(toDto(oldEntry),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ObjectId parseObjectId(String myid) {
        try {
            return new ObjectId(myid);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid journal id: " + myid);
        }
    }

    private JournalEntryDto toDto(JournalEntry entry) {
        JournalEntryDto dto = new JournalEntryDto();
        dto.setMyid(entry.getMyid() != null ? entry.getMyid().toHexString() : null);
        dto.setTitle(entry.getTitle());
        dto.setContent(entry.getContent());
        dto.setDate(entry.getDate());
        return dto;
    }

}
