package org.project.config;

import org.project.coach.Coach;
import org.project.coach.SwimCoach;
import org.project.service.FortuneService;
import org.project.service.SadFortuneService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// @ComponentScan("org.project")
@PropertySource("classpath:org/project/sport.properties")
public class SportConfig {

    @Bean
    public FortuneService sadFortuneService() {
        return new SadFortuneService();
    }

    @Bean
    public Coach swimCoach() {
        return new SwimCoach(sadFortuneService());
    }
}
