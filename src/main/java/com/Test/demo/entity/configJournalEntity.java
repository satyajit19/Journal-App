package com.Test.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "ConfigEntity")

@Data
@NoArgsConstructor
public class configJournalEntity {//POJO class-plain old java class

    private String key;
    private String value;




}
