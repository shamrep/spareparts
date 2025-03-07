package com.gymapp;


import com.gymapp.config.AppConfig;
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
        new Thread(gymApplication.tomcatServer::start).start();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GymApplication.class);

//        AppConfig.loadYamlConfig(context.getEnvironment());

        DataSource dataSource = context.getBean(DataSource.class);

        System.out.println(dataSource.hashCode());

    }

}
