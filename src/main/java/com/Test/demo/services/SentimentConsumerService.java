package com.Test.demo.services;

import com.Test.demo.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    private SendMailService emailService;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
//        ack.acknowledge();
    }

    private void sendEmail(SentimentData sentimentData) {

        emailService.mailSender(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }
}
