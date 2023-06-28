package com.edkim.webclient;

import com.edkim.webclient.hello.HelloController;
import com.edkim.webclient.hello.greeting.GreetingClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class WebclientApplication {

	public static void main(String[] args) {
/*
//		SpringApplication.run(WebclientApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(WebclientApplication.class, args);
		GreetingClient greetingClient = context.getBean(GreetingClient.class);
		// We need to block for the content here or the JVM might exit before the message is logged
		System.out.println(">> message = " + greetingClient.getMessage().block());
*/

		SpringApplication.run(WebclientApplication.class, args);
	}


}
