package com.Test.demo.services;

import com.Test.demo.Repository.JournalEntryRepository;
import com.Test.demo.Repository.UserRepository;
import com.Test.demo.entity.JournalEntry;
import com.Test.demo.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServices {
    private static final Logger logger = LoggerFactory.getLogger(JournalEntryServices.class);

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found for username: " + username);
        }
        if (user.getJournal_entries() == null) {
            user.setJournal_entries(new ArrayList<>());
        }
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournal_entries().add(saved);
        userRepository.save(user);
        logger.info("Saved journal entry {} for user {}", saved.getMyid(), username);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllUSerEntries(){

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);

    }
    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }
}
