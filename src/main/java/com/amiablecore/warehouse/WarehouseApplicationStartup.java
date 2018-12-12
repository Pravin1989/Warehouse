package com.amiablecore.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.amiablecore.warehouse")
public class WarehouseApplicationStartup {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplicationStartup.class, args);
	}
}
