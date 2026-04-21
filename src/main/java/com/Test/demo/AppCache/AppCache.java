package com.Test.demo.AppCache;

import com.Test.demo.Repository.ConfigRepository;
import com.Test.demo.entity.configJournalEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AppCache {
    @Autowired
    private ConfigRepository configRepository;

    public enum keys{
        WEATHER_API;
    }

    public Map<String,String> Cache;

    @PostConstruct
    public void init(){
        Cache = new HashMap<>();
        List<configJournalEntity> all = configRepository.findAll();
        for(configJournalEntity config:all){
            Cache.put(config.getKey(),config.getValue());
        }


    }
}
