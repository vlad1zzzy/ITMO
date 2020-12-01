package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static java.lang.Math.min;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @GetMapping("/notice")
    public String create(Model model, HttpSession httpSession) {
        if(getUser(httpSession) == null) {
            putMessage(httpSession, "You must be logged!");
            return "redirect:";
        }
        model.addAttribute("notice", new Notice());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String create(@Valid @ModelAttribute("notice") Notice notice,
                         BindingResult bindingResult,
                         HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.create(notice);
        putMessage(httpSession, "Notice "
                + notice.getContent().substring(0, min(notice.getContent().length(), 10))
                + "... successfully created!"
        );

        return "redirect:";
    }
}
