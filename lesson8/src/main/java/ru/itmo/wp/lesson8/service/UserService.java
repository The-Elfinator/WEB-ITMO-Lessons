package ru.itmo.wp.lesson8.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.lesson8.domain.User;
import ru.itmo.wp.lesson8.form.UserCredentials;
import ru.itmo.wp.lesson8.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserCredentials registerForm) {
        User user = new User();
        user.setLogin(registerForm.getLogin());
        userRepository.save(user);

        userRepository.updatePassword(user.getId(), registerForm.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByCreationTimeDesc();
    }
}
