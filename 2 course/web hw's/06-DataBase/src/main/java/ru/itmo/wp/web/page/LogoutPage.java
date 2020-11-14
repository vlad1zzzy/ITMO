package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {
    private final EventService eventService = new EventService();

    private void action(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().removeAttribute("user");
        request.getSession().setAttribute("message", "Good bye. Hope to see you soon!");
        Event event = new Event();
        event.setUserId(user.getId());
        eventService.logout(event);
        throw new RedirectException("/index");
    }
}
