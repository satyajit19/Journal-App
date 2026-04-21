package com.Test.demo.services;

import com.Test.demo.Repository.UserRepository;
import com.Test.demo.entity.UserEntity;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    Logger logger= LoggerFactory.getLogger(UserRepository.class);
    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    public void saveEntry(UserEntity User){
        try {

            User.setPassword(passwordEncoder.encode(User.getPassword()));
            User.setRoles(Arrays.asList("USER"));
            userRepository.save(User);
        } catch (Exception e) {
            logger.error("some bs error");

        }

    }
    public void saveoldentry(UserEntity User){
        userRepository.save(User);
    }

    public List<UserEntity> getAll(){

        return userRepository.findAll();
    }

    public Optional<UserEntity> getEntryById(ObjectId id){
        return userRepository.findById(id);

    }

    public UserEntity findByUsername(String Username){
        return userRepository.findByUsername(Username);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public void saveAdmin(UserEntity User) {
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        User.setRoles(Arrays.asList("ADMIN","USER"));
        userRepository.save(User);
    }

    public void addAdmin(String Username ){
        UserEntity user = userRepository.findByUsername(Username);
        user.getRoles().add("ADMIN");
        userRepository.save(user);
    }
}
