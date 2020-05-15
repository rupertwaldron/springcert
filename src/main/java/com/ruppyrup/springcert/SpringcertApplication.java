package com.ruppyrup.springcert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication // this finds all the beans
@EnableTransactionManagement
public class SpringcertApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcertApplication.class, args);
	}
}
