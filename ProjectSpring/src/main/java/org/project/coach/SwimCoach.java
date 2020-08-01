package org.project.coach;

import org.project.service.FortuneService;
import org.springframework.beans.factory.annotation.Value;

public class SwimCoach implements Coach {

    private final FortuneService fortuneService;

    @Value("${foo.email}")
    private String email;

    @Value("${foo.name}")
    private String name;

    public SwimCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getDailyFortune() {
        return fortuneService.getDailyFortune();
    }
}
