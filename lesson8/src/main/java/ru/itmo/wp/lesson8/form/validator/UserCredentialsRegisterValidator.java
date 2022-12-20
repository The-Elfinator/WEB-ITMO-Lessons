package ru.itmo.wp.lesson8.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.lesson8.form.UserCredentials;
import ru.itmo.wp.lesson8.service.UserService;

@Component
public class UserCredentialsRegisterValidator implements Validator {

    private final UserService userService;

    public UserCredentialsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCredentials.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserCredentials userCredentials = (UserCredentials) target;
            if (!userService.isLoginVacant(userCredentials.getLogin())) {
                errors.rejectValue("login", "login.in-use", "Login is already in use");
            }
        }
    }
}
