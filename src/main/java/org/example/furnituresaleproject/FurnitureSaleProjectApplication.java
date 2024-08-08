package org.example.furnituresaleproject;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FurnitureSaleProjectApplication {

//    @Bean
//    public ModelMapper initModelMapper() {
//        return new ModelMapper();
//    }

    public static void main(String[] args) {
        SpringApplication.run(FurnitureSaleProjectApplication.class, args);
    }

}
