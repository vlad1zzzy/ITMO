package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import org.checkerframework.checker.units.qual.A;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        findAll(request, view);
        putMessage(request, view);
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
        view.put("users", userService.findAll());
    }
}
