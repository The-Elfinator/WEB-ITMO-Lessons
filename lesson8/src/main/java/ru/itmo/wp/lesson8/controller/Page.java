package ru.itmo.wp.lesson8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itmo.wp.lesson8.domain.User;
import ru.itmo.wp.lesson8.service.UserService;

import javax.servlet.http.HttpSession;

public class Page {

    private static final String USER_ID_SESSION_KEY = "userId";
    private static final String MESSAGE_SESSION_KEY = "message";

    @Autowired
    private UserService userService;

    public void setUser(HttpSession httpSession, User user) {
        httpSession.setAttribute(USER_ID_SESSION_KEY, user.getId());
    }

    public void unSetUser(HttpSession httpSession) {
        httpSession.removeAttribute(USER_ID_SESSION_KEY);
    }


    @ModelAttribute("user")
    public User getUser(HttpSession httpSession) {
        Long id = (Long) httpSession.getAttribute(USER_ID_SESSION_KEY);
        return id == null ? null : userService.findById(id);
    }

    void setMessage(HttpSession httpSession, String message) {
        httpSession.setAttribute(MESSAGE_SESSION_KEY, message);
    }

    @ModelAttribute("message")
    public String getMessage(HttpSession httpSession) {
        String message = (String) httpSession.getAttribute(MESSAGE_SESSION_KEY);
        httpSession.removeAttribute(MESSAGE_SESSION_KEY);
        return message;
    }
}
