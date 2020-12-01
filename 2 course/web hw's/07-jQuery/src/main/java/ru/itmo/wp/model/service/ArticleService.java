package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.AccessException;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        if (article.getTitle().length() > 10 || article.getText().length() >= 255) {
            throw new ValidationException("To big");
        }

        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title is required");
        }
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text is required");
        }
    }

    public void create(Article article) throws ValidationException {
        validateArticle(article);
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllById(long id) {
        return articleRepository.findAllById(id);
    }

    public Article find(long id) {return articleRepository.find(id);}

    public void changeVision(Article article) {articleRepository.changeVision(article);}

    public void validateUpdate(User user, Article article) throws AccessException, ValidationException {
        if (user == null || user.getId() != article.getUserId()) {
            throw new AccessException("You can't change vision of this Article");
        }
    }
}
