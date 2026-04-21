package com.Test.demo.Scheduler;


import com.Test.demo.AppCache.AppCache;
import com.Test.demo.Repository.UserRepoImpl;
import com.Test.demo.entity.JournalEntry;
import com.Test.demo.entity.UserEntity;
import com.Test.demo.model.SentimentData;
import com.Test.demo.services.SendMailService;
import com.Test.demo.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Scheduler {

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String,SentimentData> kafkaTemplate;


    @Scheduled(cron = "0 * * ? * *")
    public void findUserAndSendMail() {
        List<UserEntity> users = userRepoImpl.getUserForSA();
        for (UserEntity user : users) {
            List<JournalEntry> journalEntries = user.getJournal_entries();
            List<String> fileteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ", fileteredEntries);
//            String sentiment = sentimentAnalysisService.getSentiment(entry);
//            sendMailService.mailSender(user.getEmail(), "Sentiment Analysis", sentiment);


            SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days Happy").build();
            try {
                kafkaTemplate.send("test-topic", sentimentData.getEmail(), sentimentData);
            } catch (Exception e) {
                sendMailService.mailSender(sentimentData.getEmail(),"Sentiments Analysis",sentimentData.getSentiment());
            }

//                try{
//                }
//                catch (Exception e){
//                    sendMailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
//                }


        }

    }

//    @Scheduled(cron="*/5 * * * *")
//    public void configCache(){
//        appCache.init();
//
//    }
}
