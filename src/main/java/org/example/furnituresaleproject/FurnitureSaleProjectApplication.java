package org.example.furnituresaleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FurnitureSaleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurnitureSaleProjectApplication.class, args);
    }

}
