package com.getir.readingisgood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan("com.getir.readingisgood")
@SpringBootApplication
public class ReadingisgoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadingisgoodApplication.class, args);
	}

}
