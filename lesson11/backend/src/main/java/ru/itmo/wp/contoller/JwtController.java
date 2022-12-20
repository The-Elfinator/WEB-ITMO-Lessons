package ru.itmo.wp.contoller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1/jwts")
public class JwtController {
    private final JwtService jwtService;
    private final UserService userService;

    private final UserCredentialsEnterValidator userCredentialsEnterValidator;

    public JwtController(JwtService jwtService, UserService userService, UserCredentialsEnterValidator userCredentialsEnterValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userCredentialsEnterValidator = userCredentialsEnterValidator;
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userCredentialsEnterValidator);
    }

    @PostMapping("")
    public String create(@Valid @RequestBody UserCredentials userCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return jwtService.create(userService.findByLoginAndPassword(
                userCredentials.getLogin(),
                userCredentials.getPassword()
        ));
    }
}
