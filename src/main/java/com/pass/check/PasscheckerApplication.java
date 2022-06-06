package com.pass.check;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
			title = "Pass Checker",
			description = "Pass Checker api",
		    version = "1.0.1"
		)
)
public class PasscheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasscheckerApplication.class, args);
	}

}
