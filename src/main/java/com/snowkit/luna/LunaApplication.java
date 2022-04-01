package com.snowkit.luna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LunaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunaApplication.class, args);
	}

}
