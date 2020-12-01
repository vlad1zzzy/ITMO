package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository extends ThingRepository<Article> {
    List<Article> findAll();

    List<Article> findAllById(long id);

    void changeVision(Article article);
}
