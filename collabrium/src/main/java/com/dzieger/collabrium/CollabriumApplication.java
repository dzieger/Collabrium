package com.dzieger.collabrium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dzieger")
public class CollabriumApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollabriumApplication.class, args);
	}

}
