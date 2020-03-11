package ru.javamentor.onlineoffice;

import org.kurento.client.KurentoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.javamentor.onlineoffice.kurento.CallHandler;
import ru.javamentor.onlineoffice.kurento.ConferenceManager;
import ru.javamentor.onlineoffice.kurento.UserRegistry;
import org.springframework.context.annotation.Bean;
import ru.javamentor.onlineoffice.entity.ActiveUserStore;

@EnableWebSocket
@SpringBootApplication
public class OnlineOfficeApplication implements WebSocketConfigurer {
	@Bean
	public UserRegistry registry() {
		return new UserRegistry();
	}

	@Bean
	public ConferenceManager conferenceManager() {
		return new ConferenceManager();
	}

	@Bean
	public CallHandler groupCallHandler() {
		return new CallHandler();
	}

	@Bean
	public KurentoClient kurentoClient() {
		return KurentoClient.create();
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlineOfficeApplication.class, args);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(groupCallHandler(), "/conference");
	}
	@Bean
	public ActiveUserStore activeUserStore(){
		return new ActiveUserStore();
	}
}
