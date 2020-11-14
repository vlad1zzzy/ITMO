package ru.itmo.wp.web.page;

import ru.itmo.wp.model.service.UserService;

import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    private void action(Map<String, Object> view) {
        view.put("users", userService.findAll());
    }
}
