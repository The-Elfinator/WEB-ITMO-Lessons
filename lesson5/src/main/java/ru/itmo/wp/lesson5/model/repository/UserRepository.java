package ru.itmo.wp.lesson5.model.repository;

import ru.itmo.wp.lesson5.model.domain.User;

public interface UserRepository {
    void register(User user, String passwordSha);

    User findByLogin(String login);

    User findByLoginAndPasswordSha(String login, String passwordSha);
}
