package com.example.tanmayee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.tanmayee.model.File;

@SpringBootApplication
@EnableConfigurationProperties({File.class})
public class SpringBootFileUploadExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFileUploadExampleApplication.class, args);
	}

}
