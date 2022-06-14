package com.appledeveloperacademy.MC2Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Mc2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mc2ServerApplication.class, args);
	}

}
