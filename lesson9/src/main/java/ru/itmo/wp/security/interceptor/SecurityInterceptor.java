package ru.itmo.wp.security.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.itmo.wp.controller.IndexPage;
import ru.itmo.wp.controller.Page;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final IndexPage indexPage;

    public SecurityInterceptor(IndexPage indexPage) {
        this.indexPage = indexPage;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if (Page.class.isAssignableFrom(method.getDeclaringClass())) {

                if (method.getAnnotation(Guest.class) != null) {
                    return true;
                }

                User user = indexPage.getUser(request.getSession());
                if (user != null) {
                    AnyRole anyRole = method.getAnnotation(AnyRole.class);
                    if (anyRole == null) {
                        return true;
                    }

                    for (Role.Name name : anyRole.value()) {
                        for (Role role : user.getRoles()) {
                            if (role.getName() == name) {
                                return true;
                            }
                        }
                    }


                }

                if (user == null) {
                    indexPage.setMessage(request.getSession(), "You should log in to the site!");
                    response.sendRedirect("/enter");
                } else {
                    indexPage.setMessage(request.getSession(), "Permission is denied!");
                    response.sendRedirect("/");
                }
                return false;
            }
        }
        return true;
    }
}
