package com.example.library.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.library")
@EntityScan(basePackages = "com.example.library.domain.entity")
@EnableJpaRepositories(basePackages = "com.example.library.domain.repo")
public class LibraryWebApplication {
  public static void main(String[] args) {
    SpringApplication.run(LibraryWebApplication.class, args);
  }
}
