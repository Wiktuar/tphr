package ru.tphr.tphr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TphrApplication {

    public static void main(String[] args) {
        SpringApplication.run(TphrApplication.class, args);
    }

}
