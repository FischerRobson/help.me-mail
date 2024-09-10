package com.helpme.mail_ms.mail_ms;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailMsApplication {

	public static void main(String[] args) {
		Dotenv dotenv = null;
		try {
			dotenv = Dotenv.load();
		} catch (Exception e) {
			System.out.println(".env file not found, assuming environment variables are set externally.");
		}

		if (dotenv != null) {
			System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
			System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
		}
		SpringApplication.run(MailMsApplication.class, args);
	}
}
