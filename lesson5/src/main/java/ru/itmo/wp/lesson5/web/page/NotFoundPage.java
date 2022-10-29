package ru.itmo.wp.lesson5.web.page;

import ru.itmo.wp.lesson5.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NotFoundPage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getParameterMap().containsKey("r")) {
            throw new RedirectException("/");
        }
    }
}
