package org.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.project.bean.Student;

import java.io.File;
import java.io.IOException;

public class JsonFileReader {

    public static final Logger logger = LogManager.getLogger(JsonFileReader.class);

    public static void read() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Student student = mapper.readValue(new File("data/sample.json"), Student.class);

            System.out.println(student);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
