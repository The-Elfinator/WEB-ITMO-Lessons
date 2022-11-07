package ru.itmo.wp.lesson5.web.page;

import ru.itmo.wp.lesson5.model.domain.User;
import ru.itmo.wp.lesson5.model.exception.ValidationException;
import ru.itmo.wp.lesson5.model.service.UserService;
import ru.itmo.wp.lesson5.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {

    }

    private void register(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        String password = request.getParameter("password");

        userService.validateRegister(user, password);

        userService.register(user, password);

        request.getSession().setAttribute("message", "You have been registered successfully!");
        throw new RedirectException("/");
    }
}
