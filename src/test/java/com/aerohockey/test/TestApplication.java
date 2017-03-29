package com.aerohockey.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.aerohockey.Application;

/**
 * Created by sergey on 21.03.17.
 */

@SpringBootApplication
@Import(Application.class)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}