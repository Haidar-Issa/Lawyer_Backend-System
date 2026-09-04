package com.web.lawyer_backend_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LawerBackEndSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LawerBackEndSystemApplication.class, args);
    }

}
