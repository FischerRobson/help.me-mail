package com.helpme.mail_ms.mail_ms;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailMsApplicationTests {

	@BeforeAll
	static void setUp() {
		Dotenv dotenv =  Dotenv.load();;
		System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
		System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
		System.setProperty("RABBITMQ_HOST", dotenv.get("RABBITMQ_HOST"));
		System.setProperty("RABBITMQ_EMAIL_QUEUE", dotenv.get("RABBITMQ_EMAIL_QUEUE"));
	}

	@Test
	void contextLoads() {
	}

}
