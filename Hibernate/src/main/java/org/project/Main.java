package org.project;

import org.project.beans.Student;

public class Main {

    public static void main(String[] args) {
        var student = new Student("John");
        DB.saveObject(student);
    }
}
