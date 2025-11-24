package com.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.common", "com.usermanagement"})
public class UsermanagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementServiceApplication.class, args);
	}

}
