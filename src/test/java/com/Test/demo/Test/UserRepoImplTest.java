package com.Test.demo.Test;

import com.Test.demo.Repository.UserRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTest {

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Test
    public void Testforuser(){
        userRepoImpl.getUserForSA();
    }

}
