package com.fitin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.fitin")
@EntityScan(basePackages = {"com.fitin.auth.entity", "com.fitin.shopping.entity", "com.fitin.exercise.selection.model",
		"com.fitin.exercise.record.model","com.fitin.exercise.video"})
public class FitinApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitinApplication.class, args);
    }
}
