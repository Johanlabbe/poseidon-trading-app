package com.poseidon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.poseidon")
public class PoseidonApplication {
    public static void main(String[] args) {
        SpringApplication.run(PoseidonApplication.class, args);
    }
}
