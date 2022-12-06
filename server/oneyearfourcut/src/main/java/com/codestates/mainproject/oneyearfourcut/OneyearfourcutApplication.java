package com.codestates.mainproject.oneyearfourcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OneyearfourcutApplication {
	public static void main(String[] args) {
		SpringApplication.run(OneyearfourcutApplication.class, args);
	}

}
