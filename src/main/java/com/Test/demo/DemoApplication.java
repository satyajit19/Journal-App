package com.Test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory databaseFactory){
		return new MongoTransactionManager(databaseFactory);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
