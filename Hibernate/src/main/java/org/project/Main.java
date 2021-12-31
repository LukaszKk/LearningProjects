package org.project;

import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.project.beans.Instructor;
import org.project.beans.InstructorDetails;
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

        var instructor = new Instructor("Name", "Email");
        var instructorDetails = new InstructorDetails("abcd", "hobby");
        instructor.setInstructorDetails(instructorDetails);

        DB.saveObject(instructor);

        var readInstructor = DB.readObject(instructor.getId(), Instructor.class);
        var readInstructorDetails = DB.readObject(instructorDetails.getId(), InstructorDetails.class);
        System.out.println(readInstructor);
        System.out.println(readInstructorDetails);
    }
}
