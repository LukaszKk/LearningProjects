package org.project;

import org.project.coach.SwimCoach;
import org.project.config.SportConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final String APP_NAME = "Spring app";

    public static void main(String[] args) {
        System.out.println(getAppName());

        var context = new AnnotationConfigApplicationContext(SportConfig.class);

        SwimCoach coach = context.getBean("swimCoach", SwimCoach.class);

        System.out.println(coach.getDailyFortune());
        System.out.println(coach.getEmail());

        context.close();
    }

    private static String getAppName() {
        return APP_NAME;
    }
}
