package com.example.tmsaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Users API", version = "2.0", description = "Users Information"))
public class TmsaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmsaApiApplication.class, args);
	}

}
