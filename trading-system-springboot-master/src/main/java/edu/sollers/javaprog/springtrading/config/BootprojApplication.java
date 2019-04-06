package edu.sollers.javaprog.springtrading.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BootprojApplication   {
	
	public static void main(String[] args) {
		SpringApplication.run(BootprojApplication.class, args);
	}
}
