package com.dzieger.collabrium;

import com.dzieger.services.DatabaseSeederService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Entry point for the Collabrium Application.
 *
 * This is a project management application designed for teams.
 * <p>
 * Responsibilities:
 * - Configures and initializes the application context.
 * - Seeds the database with initial data upon startup.
 * </p>
 * <p>
 * Annotations:
 * - {@code @SpringBootApplication}: Combines {@code @Configuration}, {@code @ComponentScan}, and {@code @EnableAutoConfiguration}.
 * - {@code @EntityScan}: Scans for JPA entities in specified packages.
 * - {@code @EnableJpaRepositories}: Enables JPA repositories in specified packages.
 * </p>
 *
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan("com.dzieger")
@EntityScan("com.dzieger.models")
@EnableJpaRepositories("com.dzieger.repositories")
public class CollabriumApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(CollabriumApplication.class);

	private final DatabaseSeederService databaseSeederService;

	/**
	 * Constructs a new CollabriumApplication instance.
	 *
	 * @param databaseSeederService the service responsible for database seeding
	 */
	public CollabriumApplication(DatabaseSeederService databaseSeederService) {
		this.databaseSeederService = databaseSeederService;
	}

	/**
	 * Entry point for the application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CollabriumApplication.class, args);
		log.info("Collabrium Application has started successfully.");
	}

	/**
	 * Executes database seeding after the application context is loaded.
	 *
	 * @param args the command line arguments
	 */
	@Override
	public void run(String... args) {
		try {
			log.debug("Starting database seeding...");
			databaseSeederService.seedDatabase();
			log.info("Database seeding completed successfully.");
		} catch (Exception e) {
			log.error("Database seeding failed", e);
		}
	}
}

