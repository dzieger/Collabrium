package com.dzieger.collabrium;

import com.dzieger.services.DatabaseSeederService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * CollabriumApplication
 *
 * A project management application for teams.
 *
 * This class is the entry point for the application.
 *
 * It is annotated with @SpringBootApplication, which is a convenience annotation that adds the following annotations:
 * - @Configuration: Indicates that this class is a configuration class.
 * - @ComponentScan: Tells Spring Boot to scan for components in the current package and its sub-packages.
 * - @EntituScan: Tells Spring Boot to scan for JPA entities in the current package and its sub-packages.
 * - @EnableJpaRepositories: Tells Spring Boot to scan for JPA repositories in the current package and its sub-packages.
 *
 * It also implements CommandLineRunner, which provides a callback method that will be executed after the
 * application context is loaded.
 *
 * @version 1.0
 */
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
