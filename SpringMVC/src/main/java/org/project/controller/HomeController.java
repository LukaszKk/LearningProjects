package org.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String showHomePage() {
        return "home";
    }

    @RequestMapping("/formPage")
    public String showFormPage() {
        return "formpage";
    }

    @RequestMapping("/processForm")
    public String processForm() {
        return "helloworld";
    }

    @RequestMapping("/processFormVersion2")
    public String letsShoutDude(HttpServletRequest request, Model model) {
        String name = request.getParameter("studentName");
        name = name.toUpperCase();
        String result = "Yo! " + name;

        model.addAttribute("message", result);

        return "helloworld";
    }
}
