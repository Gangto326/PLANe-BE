package com.plane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PLANeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PLANeApplication.class, args);
	}

}
