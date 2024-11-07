package com.fitin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
@SpringBootApplication(scanBasePackages = "com.fitin")
@EntityScan(basePackages = {
    "com.fitin.auth.entity", 
    "com.fitin.shopping.entity", 
    "com.fitin.exercise.selection.model",
    "com.fitin.exercise.record.model",
    "com.fitin.exercise.video",
    "com.fitin.profile.entity",
    "com.fitin.community.common.model",
    "com.fitin.community.diary.model",
    "com.fitin.community.routine.model",
    "com.fitin.community.challenge.model",
    "com.fitin.community.gamification.model"
})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FitinApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitinApplication.class, args);
    }
}
