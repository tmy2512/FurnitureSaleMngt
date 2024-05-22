package org.example.furnituresaleproject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ComponentConfig {

    @Bean
    public ModelMapper initModelMapper() {
        return new ModelMapper();
    }

}
