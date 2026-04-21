package com.Test.demo.controller;

import com.Test.demo.Repository.UserRepository;
import com.Test.demo.api.response.WeatherEntity;
import com.Test.demo.entity.JournalEntry;
import com.Test.demo.entity.UserEntity;
import com.Test.demo.services.JournalEntryServices;
import com.Test.demo.services.UserServices;
import com.Test.demo.services.WeatherServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherServices weatherServices;

    @DeleteMapping()
    public boolean deleteEntry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUsername(username);
        return true;
    }

    @PutMapping()
    public ResponseEntity<?> updateUserByUsername(@RequestBody UserEntity user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity olduser =  userServices.findByUsername(username);
        olduser.setUsername(user.getUsername());
        olduser.setPassword(user.getPassword());
        userServices.saveEntry(olduser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> greating(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherEntity weatherResponse = weatherServices.getweather("Mumbai");
        String greeting ="";
        if(weatherResponse!=null){
            greeting ="Weather feels like"+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+authentication.getName()+" "+greeting,HttpStatus.OK);

    }

}
