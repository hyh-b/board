package com.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(BoardApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
