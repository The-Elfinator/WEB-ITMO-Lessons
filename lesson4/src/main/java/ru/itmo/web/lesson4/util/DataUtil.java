package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "mike", "Mike Mirzayanov"),
            new User(5, "geranazarov555", "Georgiy Nazarov"),
            new User(10, "pashka", "Pavel Mavrin"),
            new User(11, "tourist", "Gennady Korotkevich")
    );

    public static void addData(Map<String, Object> data) {
        data.put("users", USERS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(data.get("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
