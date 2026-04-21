package com.Test.demo.Test;

import com.Test.demo.services.SendMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailServiceTest {

    @Autowired
    private SendMailService sendMailService;

    @Test
    public void sendMailTest(){
        sendMailService.mailSender("rckr225@gmail.com","main mission","hi lodu");

    }
}
