package ch.bluecare.springplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    // Note a sample Spring Boot Project can be created with the Spring Initializr
    // https://start.spring.io/

    // Spring will scan all components of sub-packages. That's why all controllers are accessible
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
