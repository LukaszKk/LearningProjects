package org.project;

import org.project.beans.Student;

public class Main {

    public static void main(String[] args) {
        var student = new Student("John");
        DB.saveObject(student);

        var johnObject = DB.readObject(student.getId(), Student.class);
        System.out.println(johnObject);

        var johnsList = DB.readObjectsWhere(Student.class.getName(), "name", "=", "John");
        System.out.println(johnsList);
    }
}
