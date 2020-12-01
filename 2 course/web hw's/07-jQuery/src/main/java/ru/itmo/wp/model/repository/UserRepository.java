package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface UserRepository extends ThingRepository<User> {
    User findByLoginOrEmail(String info, String field);
    User findByLoginAndPasswordSha(String info, String passwordSha, String field);
    long findCount();
    List<User> findAll();
    void save(User user, String passwordSha);

    void changeAdmin(User user);
}