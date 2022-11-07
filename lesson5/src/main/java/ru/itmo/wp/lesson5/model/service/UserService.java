package ru.itmo.wp.lesson5.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.lesson5.model.domain.User;
import ru.itmo.wp.lesson5.model.exception.ValidationException;
import ru.itmo.wp.lesson5.model.repository.UserRepository;
import ru.itmo.wp.lesson5.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;

public class UserService {

    private static final int loginMaxLength = 24;
    private static final int passwordMaxLength = 64;
    private static final int passwordMinLength = 4;
    private static final String PASSWORD_SALT = "734a728d4a3a870404fb4abf72723c754296";

    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegister(User user, String password) {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }

        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login should contain only lowercase Latin letters");
        }

        if (user.getLogin().length() > loginMaxLength) {
            throw new ValidationException("Login should contain at most " + loginMaxLength + " letters");
        }

        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use!");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }

        if (password.length() < passwordMinLength) {
            throw new ValidationException("Login should contain at least " + passwordMinLength + " characters");
        }

        if (password.length() > passwordMaxLength) {
            throw new ValidationException("Password should contain at most " + passwordMaxLength + " characters");
        }



    }

    public void register(User user, String password) {
        userRepository.register(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public void validateEnter(String login, String password) {
        if (Strings.isNullOrEmpty(login)) {
            throw new ValidationException("Login is required");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }

        if (userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password)) == null) {
            throw new ValidationException("Incorrect login or password");
        }

    }

    public User findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
    }
}
