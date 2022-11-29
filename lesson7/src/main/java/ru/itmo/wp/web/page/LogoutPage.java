package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class LogoutPage {
    private void action(HttpServletRequest request, Map<String, Object> view) {

        saveEvent((User) request.getSession().getAttribute("user"));

        request.getSession().removeAttribute("user");
        request.getSession().setAttribute("message", "Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }

    private void saveEvent(User user) {
        final EventRepository eventRepository = new EventRepositoryImpl();
        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(Event.Type.LOGOUT);
        eventRepository.save(event);
    }
}
