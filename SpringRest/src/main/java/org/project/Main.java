package org.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.project.bean.Student;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // enable log4j2
        PluginManager.addPackage("org.project");

        ObjectMapper mapper = new ObjectMapper();

        try {
            Student student = mapper.readValue(new File("data/sample.json"), Student.class);

            System.out.println(student);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
