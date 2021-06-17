package com.githubscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GithubScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubScraperApplication.class, args);
    }

}
