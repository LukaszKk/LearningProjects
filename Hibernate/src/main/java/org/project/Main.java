package org.project;

import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.hibernate.Hibernate;
import org.project.beans.Course;
import org.project.beans.Instructor;
import org.project.beans.InstructorDetails;
import org.project.beans.Student;

import javax.management.Query;

public class Main {

    public static void main(String[] args) {
        // enable log4j2
        PluginManager.addPackage("org.project");

        // save and read basic objects
        var student = new Student("John");
        DB.saveObject(student);

        var johnObject = DB.readObject(student.getId(), Student.class);
        System.out.println(johnObject);

        var johnsList = DB.readObjectsWhere(Student.class.getName(), "name", "=", "John");
        System.out.println(johnsList);

        // test @OneToOne mapping saving
        var instructor = new Instructor("Name", "Email");
        var instructorDetails = new InstructorDetails("abcd", "hobby");
        instructor.setInstructorDetails(instructorDetails);

        DB.saveObject(instructor);

        var readInstructor = DB.readObject(instructor.getId(), Instructor.class);
        var readInstructorDetails = DB.readObject(instructorDetails.getId(), InstructorDetails.class);
        System.out.println(readInstructor);
        System.out.println(readInstructorDetails);
        System.out.println(readInstructorDetails.getInstructor());

        // remove instructor and instructorDetails
        DB.removeObject(readInstructor);

        // Test @OneToMany: save some courses and verify its existence in Instructor table
        System.out.println("\nOne to Many");
        instructor = new Instructor("Name", "Email");
        DB.saveObject(instructor);
        var course1 = new Course("Course 1");
        var course2 = new Course("Course 2");
        instructor.add(course1);
        instructor.add(course2);

        DB.saveObject(course1);
        DB.saveObject(course2);
        readInstructor = DB.executeQuery("SELECT i FROM Instructor i " +
                "JOIN i.courses " +
                "WHERE i.id=" + instructor.getId(), Instructor.class);

        System.out.println(readInstructor.getCourses());
        DB.removeObject(course2);
        readInstructor = DB.readObject(instructor.getId(), Instructor.class);
        System.out.println(readInstructor.getCourses());

        //
    }
}
