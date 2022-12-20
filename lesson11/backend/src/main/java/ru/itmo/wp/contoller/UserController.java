package ru.itmo.wp.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.JwtService;

@RestController
@RequestMapping("/api/1/users")
public class UserController {
    private final JwtService jwtService;

    public UserController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("jwt")
    public User findByJwt(@RequestParam(name = "jwt") String jwt) {
        return jwtService.findUser(jwt);
    }
}
