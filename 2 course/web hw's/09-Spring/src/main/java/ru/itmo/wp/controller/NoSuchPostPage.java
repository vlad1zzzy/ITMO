package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoSuchPostPage extends Page {
    @GetMapping("/noSuchPostPage")
    public String index() {
        return "NoSuchPostPage";
    }
}
