package com.dictionary.config;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("com.ecfinder")
public class AppConfig {

    @Value("${alphabet.server}")
    private String alphabetServer;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(context -> (!(context.getSource() instanceof PersistentCollection)
                || ((PersistentCollection) context.getSource()).wasInitialized()));
        return modelMapper;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create(alphabetServer);
    }

}
