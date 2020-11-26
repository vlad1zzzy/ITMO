package ru.itmo.wp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ErrorPage extends Page implements ErrorController {

    @GetMapping("/error")
    public String errorHandler(HttpSession httpSession) {
        putMessage(httpSession, "NO SUCH PAGE!");
        return "forward:";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
