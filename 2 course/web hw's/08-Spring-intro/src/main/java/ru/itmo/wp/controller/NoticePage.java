package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.NoticeForm;
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
        model.addAttribute("noticeForm", new NoticeForm());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String create(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm,
                         BindingResult bindingResult,
                         HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        Notice notice = noticeService.create(noticeForm);
        putMessage(httpSession, "Notice "
                + notice.getContent().substring(0, min(notice.getContent().length(), 10))
                + "... successfully created!"
        );

        return "redirect:";
    }
}
