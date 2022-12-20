package ru.itmo.wp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.User;

@Service
public class JwtService {

    private static final String SECRET = "a511b276a93972d372147b60731d6a39503d58c1";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public String create(User user) {
        try {
            return JWT.create()
                    .withClaim("userId", user.getId())
                    .sign(ALGORITHM);
        } catch (Exception e){
            throw new RuntimeException("Can't create JWT.", e);
        }
    }

    public User findUser(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .build();

            Long userId = verifier.verify(jwt).getClaim("userId").asLong();
            return userService.findById(userId);
        } catch (Exception e) {
            return null;
        }
    }
}
