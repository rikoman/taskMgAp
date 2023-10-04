package com.example.sendingmessagesemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SendingMessagesEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendingMessagesEmailApplication.class, args);
	}

}
