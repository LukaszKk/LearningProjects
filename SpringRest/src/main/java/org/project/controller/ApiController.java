package org.project.controller;

import org.project.bean.Person;
import org.project.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private List<Person> people;

    @PostConstruct
    public void loadData() {
        people = new ArrayList<>();
        people.add(new Person("A", "B"));
        people.add(new Person("C", "D"));
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping("/people")
    public List<Person> getPeople() {
        return people;
    }

    @GetMapping("/people/{personId}")
    public Person getPerson(@PathVariable int personId) {
        if (people.size() <= personId || personId < 0) {
            throw new NotFoundException("Id not found: " + personId);
        }

        return people.get(personId);
    }
}
