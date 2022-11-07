package ru.itmo.wp.lesson5.web.page;

import ru.itmo.wp.lesson5.model.domain.User;
import ru.itmo.wp.lesson5.model.exception.ValidationException;
import ru.itmo.wp.lesson5.model.service.UserService;
import ru.itmo.wp.lesson5.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {

    }

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        userService.validateEnter(login, password);
        User user = userService.findByLoginAndPassword(login, password);

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("message", "Hello, " + user.getLogin() + "!");
        throw new RedirectException("/");
    }
}
