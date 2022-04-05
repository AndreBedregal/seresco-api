package com.github.andrebedregal.seresco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SerescoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerescoApplication.class, args);
    }

}
