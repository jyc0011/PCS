package com.example.pcs.PCS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.pcs.PCS.domain")
public class PcsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PcsApplication.class, args);
    }
}
