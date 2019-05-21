package se.nackademin.restcms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RestcmsApplication {


    public static void main(String[] args) {
        SpringApplication.run(RestcmsApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {

        };
    }
}
