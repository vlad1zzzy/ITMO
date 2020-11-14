package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.BaseRepository;

import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class.getSimpleName());
    }

    public User findByLoginOrEmail(String info, String field) {
        return findBy(
                "SELECT * FROM User WHERE " + field + "=?",
                new Object[]{info}
        );
    }

    public User findByLoginAndPasswordSha(String info, String passwordSha, String field) {
        return findBy(
                "SELECT * FROM User WHERE " + field + "=? AND passwordSha=?",
                new Object[]{info, passwordSha}
        );
    }

    public List<User> findAll() {
        return findAllBy(
                "SELECT * FROM User ORDER BY id DESC",
                null
        );
    }

    public User toThing(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    public void save(User user) {}

    public void save(User user, String passwordSha) {
        saveThing(
                user,
                "INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, NOW())",
                new Object[]{user.getLogin(), user.getEmail(), passwordSha}
        );
    }
}
