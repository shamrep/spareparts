package com.spareparts.store;

import com.spareparts.store.controller.FrontController;
import com.sun.net.httpserver.HttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.InetSocketAddress;

//@SpringBootApplication
public class GymApplication {
	public static void main(String[] args) throws IOException {
		int port = 8080; // Server port
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		//SpringApplication.run(GymApplication.class, args);
		server.createContext("/", new FrontController());
		// Start the server
		server.setExecutor(null); // Default executor
		System.out.println("Server started on port " + port);
		server.start();
	}
}
