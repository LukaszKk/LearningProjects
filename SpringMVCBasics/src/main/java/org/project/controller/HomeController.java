package org.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String mainPage(@RequestParam(name = "name", required = false,
            defaultValue = "World") String name, Model model) {

        model.addAttribute("name", name);
        return "home";
    }

}
