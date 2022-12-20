package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;

    public WritePostPage(UserService userService) {
        this.userService = userService;
    }

    @AnyRole(Role.Name.WRITER)
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("post", new Post());
        return "WritePostPage";
    }

    @PostMapping("/writePost")
    public String registerPost(@Valid @ModelAttribute("post") Post post,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }

        userService.writePost(getUser(httpSession), post);

        setMessage(httpSession, "Post has been published successfully!");

        return "redirect:/";
    }
}
