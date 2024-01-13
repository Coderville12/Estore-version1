package com.gp.electro.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication

public class EStoreApplication {

	public static void main(String[] args) {
		// System.setProperty("spring.config.name", "my-project");
		System.setProperty("spring.config.name", "gunjan");
		SpringApplication.run(EStoreApplication.class, args);
		
	
	}

}
