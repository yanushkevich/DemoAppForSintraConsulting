package com.sintra.loanapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableCaching
public class FinWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinWebApplication.class, args);
	}
}
