package org.project;

import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.project.beans.Student;

public class Main {

    public static void main(String[] args) {
        PluginManager.addPackage("org.project");

        var student = new Student("John");
        DB.saveObject(student);

        var johnObject = DB.readObject(student.getId(), Student.class);
        System.out.println(johnObject);

        var johnsList = DB.readObjectsWhere(Student.class.getName(), "name", "=", "John");
        System.out.println(johnsList);
    }
}
