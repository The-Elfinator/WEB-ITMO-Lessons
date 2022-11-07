package ru.itmo.wp.lesson5.web.page;

import ru.itmo.wp.lesson5.model.domain.User;
import ru.itmo.wp.lesson5.model.exception.ValidationException;
import ru.itmo.wp.lesson5.model.service.UserService;
import ru.itmo.wp.lesson5.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        request.getSession().removeAttribute("user");
        request.getSession().setAttribute("message", "Good bye!");
    }

}
