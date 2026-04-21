package com.Test.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Journal_Entries")

@Data
@NoArgsConstructor
public class JournalEntry {//POJO class-plain old java class

    @Id
    private ObjectId myid;
    private String title;
    private String content;
    private LocalDateTime date;



}
