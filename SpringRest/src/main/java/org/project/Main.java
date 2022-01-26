package org.project;

import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // enable log4j2
        PluginManager.addPackage("org.project");

        JsonFileReader.read();

        SpringApplication.run(Main.class, args);
    }
}
