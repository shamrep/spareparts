package com.gymapp.config;

import com.gymapp.repository.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class AppConfig {

    @Bean
    public ClientRepository getClientRepository() {
//        return new ClientRepository();
        //todo: find out how to add bean with datasource
//        return new ClientRepository();

        return null;
    }

}
