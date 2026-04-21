package com.Test.demo.Repository;

import com.Test.demo.entity.JournalEntry;
import com.Test.demo.entity.configJournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<configJournalEntity, ObjectId> {
}
