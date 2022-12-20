package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;

@Controller
public class MyPostsPage extends Page {

    @AnyRole({Role.Name.ADMIN, Role.Name.WRITER})
    @GetMapping("/myPosts")
    public String users() {
        return "MyPostsPage";
    }
}
