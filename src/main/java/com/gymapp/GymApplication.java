package com.gymapp;


import com.gymapp.repository.ClientRepository;
import com.gymapp.repository.entity.ClientEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
@ComponentScan("com.gymapp")
public class GymApplication {

    private final EmbeddedTomcatServer tomcatServer;

    public GymApplication(EmbeddedTomcatServer tomcatServer) {
        this.tomcatServer = tomcatServer;
    }

    public GymApplication() {
        this(new EmbeddedTomcatServer());
    }

    public static void main(String[] args) {

        GymApplication gymApplication = new GymApplication();
        
        Thread t = new Thread(gymApplication.tomcatServer::start);
        t.setDaemon(true);
        t.start();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GymApplication.class);

        ClientRepository repository = context.getBean(ClientRepository.class);

        // Save a new client
        ClientEntity client = new ClientEntity(null, "test@example.com", "John Doe", "securepassword");

        repository.save(client);

        System.out.println(repository.existsByEmail("test@example.com"));

    }

}
