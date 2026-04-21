package com.Test.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {//POJO class-plain old java class

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;

    private String email;
    private boolean sentimentAnalysis;
    @DBRef
    private List<JournalEntry> Journal_entries =new ArrayList<>();
    private List<String> roles;




}
