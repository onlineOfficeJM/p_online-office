package ru.javamentor.onlineoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.javamentor.onlineoffice.entity.ActiveUserStore;

@SpringBootApplication
public class OnlineOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineOfficeApplication.class, args);
	}

	@Bean
	public ActiveUserStore activeUserStore(){
		return new ActiveUserStore();
	}
}
