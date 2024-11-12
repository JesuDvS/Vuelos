package com.example.Vuelos;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VuelosApplication {
	private static ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		System.getProperties().put("server.port", "8081"); // Cambia el puerto aquí
		springContext = SpringApplication.run(VuelosApplication.class, args);
		Application.launch(FlightApp.class, args);
	}

	public static ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}
}