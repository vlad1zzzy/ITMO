package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class Page {
    private final UserService userService = new UserService();
    HttpServletRequest request_;

    void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    void before(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
        request_ = request;
        setUser(view);
        setMessage(view);
    }

    void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    void setUser(Map<String, Object> view) {
        User user = (User) request_.getSession().getAttribute("user");
        if (user != null)
            view.put("user", user);
    }

    User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    void setMessage(Map<String, Object> view) {
        String message = (String) request_.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request_.getSession().removeAttribute("message");
        }
    }

}
