package org.project.controller;

import org.project.bean.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping("/people")
    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("A", "B"));
        people.add(new Person("C", "D"));

        return people;
    }
}
