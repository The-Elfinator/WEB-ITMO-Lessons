package ru.itmo.wp.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public List<Post> findAll() {
        return postService.findAll();
    }
}
