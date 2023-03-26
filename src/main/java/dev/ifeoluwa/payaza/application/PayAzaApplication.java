package dev.ifeoluwa.payaza.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class PayAzaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayAzaApplication.class, args);
    }

}
