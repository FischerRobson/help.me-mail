package com.helpme.notification_ms;

import io.github.cdimascio.dotenv.Dotenv;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NotificationMsApplication {
	private static final Logger logger = LoggerFactory.getLogger(NotificationMsApplication.class);

	public static void main(String[] args) {
		AnsiConsole.systemInstall();

		Dotenv dotenv = null;
		try {
			dotenv = Dotenv.load();
		} catch (Exception e) {
			logger.info(".env file not found, assuming environment variables are set externally.");
		}

		if (dotenv != null) {
			System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
			System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
			System.setProperty("RABBITMQ_HOST", dotenv.get("RABBITMQ_HOST"));
			System.setProperty("RABBITMQ_NOTIFICATION_QUEUE", dotenv.get("RABBITMQ_NOTIFICATION_QUEUE"));
			System.setProperty("RABBITMQ_DEAD_LETTER_QUEUE", dotenv.get("RABBITMQ_DEAD_LETTER_QUEUE"));
		}
		SpringApplication.run(NotificationMsApplication.class, args);
		AnsiConsole.systemUninstall();
	}
}
