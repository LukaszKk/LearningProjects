package org.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SillyController {

    @RequestMapping("/")
    public String displayHome() {
        return "home";
    }

    @RequestMapping("/showForm")
    public String displayTheForm() {
        return "silly";
    }
}
