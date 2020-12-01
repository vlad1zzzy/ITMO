package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.BaseRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ArticleRepositoryImpl extends BaseRepository<Article> implements ArticleRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public ArticleRepositoryImpl() {
        super(Article.class.getSimpleName());
    }

    protected Article toThing(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return article;
    }

    @Override
    public void save(Article article) {
        saveThing(article,
                "INSERT INTO `Article` (`userId`, `title`, `text`,`hidden`, `creationTime`) VALUES (?, ?, ?, ?, NOW())",
                new Object[] {article.getUserId(), article.getTitle(), article.getText(), article.isHidden()});
    }

    @Override
    public List<Article> findAll() {
        return findAllBy(
                "SELECT * FROM Article ORDER BY id DESC",
                null
        );
    }


    public List<Article> findAllById(long id) {
        return findAllBy(
                "SELECT * FROM Article WHERE userId=? ORDER BY id DESC",
                new Object[]{id}
        );
    }

/*
    public void changeVision(Article article) {
        article.setHidden(!article.isHidden());
        saveThing(article,
                "UPDATE Article SET hidden=? WHERE id=?",
                new Object[] {article.isHidden(), article.getId()}
                );
    }
*/

    public void changeVision(Article article) {
        article.setHidden(!article.isHidden());
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE Article SET hidden=? WHERE id=?")) {
                statement.setBoolean(1, article.isHidden());
                statement.setLong(2, article.getId());
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save Article.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Article.", e);
        }
    }
}
