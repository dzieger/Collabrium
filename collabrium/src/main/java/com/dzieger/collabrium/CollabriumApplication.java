package com.dzieger.collabrium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.dzieger")
@EntityScan("com.dzieger.models")
@EnableJpaRepositories("com.dzieger.repositories")
public class CollabriumApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollabriumApplication.class, args);
	}

}
