package com.dzieger.collabrium;

import com.dzieger.services.DatabaseSeederService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.dzieger")
@EntityScan("com.dzieger.models")
@EnableJpaRepositories("com.dzieger.repositories")
public class CollabriumApplication implements CommandLineRunner {

	private final DatabaseSeederService databaseSeederService;

	public CollabriumApplication(DatabaseSeederService databaseSeederService) {
		this.databaseSeederService = databaseSeederService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CollabriumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		databaseSeederService.seedDatabase();
	}

}
