package com.Test.demo.controller;

import com.Test.demo.AppCache.AppCache;
import com.Test.demo.entity.UserEntity;
import com.Test.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private AppCache appCache;

    @GetMapping("all-user")
    public List<UserEntity> getall(){
        return userServices.getAll();
    }

    @PostMapping("create-admin")
    public ResponseEntity<UserEntity> createadmin(@RequestBody UserEntity user){
        userServices.saveAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("add-admin")
    public ResponseEntity<?> addAdmin(@RequestBody String Username){
        userServices.addAdmin(Username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("clear-cache")
    public void clearCache(){
        appCache.init();
    }
}
