package org.project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HomeController {

    @Value("${info.app.name}")
    private String appName;

    @GetMapping("/")
    public String home() {
        return "Hello boot at " + LocalDateTime.now();
    }

    @GetMapping("/name")
    public String appName() {
        return appName;
    }
}
