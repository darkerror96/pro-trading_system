package edu.sollers.javaprog.springtrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author rutpatel
 *
 */
@SpringBootApplication
@EnableJpaRepositories
public class BootprojApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootprojApplication.class, args);
	}
}
