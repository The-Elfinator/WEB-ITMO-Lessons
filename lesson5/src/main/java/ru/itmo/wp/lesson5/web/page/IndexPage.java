package ru.itmo.wp.lesson5.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("name", "Mike");
    }
}
