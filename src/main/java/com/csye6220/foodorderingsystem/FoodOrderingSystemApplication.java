package com.csye6220.foodorderingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication (exclude = {HibernateJpaAutoConfiguration.class})
public class FoodOrderingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderingSystemApplication.class, args);
	}

}
