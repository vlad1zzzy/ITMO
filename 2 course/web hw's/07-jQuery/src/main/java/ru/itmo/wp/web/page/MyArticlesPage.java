package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.AccessException;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "MyArticles page available only for authorized users");
            throw new RedirectException("/index");
        }
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        view.put("articles", articleService.findAllById(user.getId()));
    }

    private void changeVision(HttpServletRequest request, Map<String, Object> view) throws AccessException, ValidationException {
        long id = Long.parseLong(request.getParameter("articleId"));
        Article article = articleService.find(id);

        User user = (User) request.getSession().getAttribute("user");
        articleService.validateUpdate(user, article);

        boolean value = request.getParameter("value").equals("Show");
        if (value == article.isHidden()) {
            request.getSession().setAttribute("message", "Reloaded page for actual data");
            throw new RedirectException("/myArticles");
        }

        articleService.changeVision(article);
        view.put("article", article);
    }
}
