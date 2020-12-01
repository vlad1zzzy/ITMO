package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.AccessException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeAdmin(HttpServletRequest request, Map<String, Object> view) throws AccessException {

        User loggedUser = (User) request.getSession().getAttribute("user");
        userService.validateUpdate(loggedUser);

        User changeUser = userService.find(Long.parseLong(request.getParameter("idChange")));
        boolean value = request.getParameter("value").equals("Enable");
        if (value != changeUser.isAdmin()) {
            request.getSession().setAttribute("message", "Reloaded page for actual data");
            throw new RedirectException("/users");
        }

        userService.changeAdmin(changeUser);
        view.put("user", loggedUser);


        changeUser.setAdmin(!changeUser.isAdmin());

        if (changeUser.getId() == loggedUser.getId()) {
            loggedUser.setAdmin(false);
            view.put("user", loggedUser);
            throw new RedirectException("/users");
        }

    }
}
