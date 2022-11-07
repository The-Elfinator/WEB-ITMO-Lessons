package ru.itmo.wp.lesson5.web;

import freemarker.template.*;
import ru.itmo.wp.lesson5.model.exception.ValidationException;
import ru.itmo.wp.lesson5.web.exception.NotFoundException;
import ru.itmo.wp.lesson5.web.exception.RedirectException;
import ru.itmo.wp.lesson5.web.page.IndexPage;
import ru.itmo.wp.lesson5.web.page.NotFoundPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FrontServlet extends HttpServlet {

    private Configuration sourceTemplateConfiguration;
    private Configuration targetTemplateConfiguration;

    @Override
    public void init() throws ServletException {
        super.init();

        sourceTemplateConfiguration = newConfiguration(new File(
                getServletContext().getRealPath("/"),
                "../../src/main/webapp/WEB-INF/templates"
        ), true);

        targetTemplateConfiguration = newConfiguration(new File(
                getServletContext().getRealPath("/WEB-INF/templates")
        ), false);
    }

    private Configuration newConfiguration(File dir, boolean debug) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);

        try {
            configuration.setDirectoryForTemplateLoading(dir);
        } catch (IOException ignored) {
            return null;
        }

        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(debug ?
                TemplateExceptionHandler.DEBUG_HANDLER :
                TemplateExceptionHandler.RETHROW_HANDLER);

        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);

        return configuration;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Route route = Route.newRoute(request);

        try {
            process(route, request, response);
        } catch (NotFoundException e) {
            try {
                process(Route.newNotFoundRoute(), request, response);
            } catch (NotFoundException ex) {
                throw new ServletException(ex);
            }
        }
    }

    private void process(Route route, HttpServletRequest request, HttpServletResponse response) throws NotFoundException, ServletException, IOException {

        Class<?> pageClass;
        try {
            pageClass = Class.forName(route.getClassName());
        } catch (ClassNotFoundException ignored) {
            throw new NotFoundException();
        }

        Method method = null;
        for (Class<?> c = pageClass; method == null && c != null; c = c.getSuperclass()) {
            try {
                method = c.getDeclaredMethod(route.getAction(), HttpServletRequest.class, Map.class);
            } catch (NoSuchMethodException ignored) {
                // No operations.
            }

        }

        if (method == null) {
            throw new NotFoundException();
        }

        Object page;
        try {
            page = pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ServletException("Can't create page instance [pageClass=" + pageClass + ", action=" + route.getAction() + "].", e);
        }

        Map<String, Object> view = new HashMap<>();

        method.setAccessible(true);

        try {
            method.invoke(page, request, view);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();

            if (cause instanceof RedirectException) {
                RedirectException redirectException = (RedirectException) cause;
                response.sendRedirect(redirectException.getTarget());
                return;
            } else if (cause instanceof ValidationException) {
                view.put("error", cause.getMessage());
                for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                    if (entry.getValue() != null && entry.getValue().length == 1) {
                        view.put(entry.getKey(), entry.getValue()[0]);
                    }
                }
            } else {
                throw new ServletException("Unexpected page exception [pageClass=" + pageClass + ", action=" + route.getAction() + "].", e);
            }
        }

        if (request.getSession().getAttribute("user") != null) {
            view.put("user", request.getSession().getAttribute("user"));
        }

        Template template = newTemplate(pageClass.getSimpleName() + ".ftlh");

        response.setContentType("text/html");
        try {
            template.process(view, response.getWriter());
        } catch (TemplateException e) {
            throw new ServletException("Can't process template [pageClass=" + pageClass
                    + ", action=" + route.getAction() + "].", e);
        }
    }

    private Template newTemplate(String templateName) throws ServletException {
        Template template = null;

        if (sourceTemplateConfiguration != null) {
            try {
                template = sourceTemplateConfiguration.getTemplate(templateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName="
                        + templateName + "].", e);
            }
        }

        if (template == null && targetTemplateConfiguration != null) {
            try {
                template = targetTemplateConfiguration.getTemplate(templateName);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [templateName="
                        + templateName + "].", e);
            }
        }

        if (template == null) {
            throw new ServletException("Can't find template file [templateName=" + templateName + "].");
        }

        return template;
    }

    private static final class Route {
        private static final String BASE_PAGE_PACKAGE = FrontServlet.class.getPackage().getName() + ".page";
        private static final String DEFAULT_ACTION = "action";
        private final String className;
        private final String action;

        private Route(String className, String action) {
            this.className = className;
            this.action = action;
        }

        public static Route newNotFoundRoute() {
            return new Route(NotFoundPage.class.getName(), DEFAULT_ACTION);
        }


        public static Route newIndexRoute(String action) {
            return new Route(IndexPage.class.getName(), action);
        }

        public String getClassName() {
            return className;
        }

        public String getAction() {
            return action;
        }

        // NOTE: example of what newRoute do
        // URI: /misc/help
        // ["", "misc", "help"]
        // ["misc", "help"]
        // ["misc", "HelpPage"]
        // Must get: ru.itmo.wp.lesson5.web.page.misc.HelpPage

        private static Route newRoute(HttpServletRequest request) {
            String action = request.getParameter("action");
            if (action == null || action.isEmpty()) {
                action = DEFAULT_ACTION;
            }


            List<String> classNameParts = Arrays.stream(request.getRequestURI().split("/"))
                    .filter(item -> !item.isEmpty())
                    .collect(Collectors.toList());

            if (classNameParts.isEmpty()) {
                return newIndexRoute(action);
            }

            StringBuilder simpleClassName
                    = new StringBuilder(classNameParts.get(classNameParts.size() - 1));
            simpleClassName.setCharAt(0, Character.toUpperCase(simpleClassName.charAt(0)));
            simpleClassName.append("Page");
            classNameParts.set(classNameParts.size()-1, simpleClassName.toString());

            String className = BASE_PAGE_PACKAGE + "." + String.join(".", classNameParts);


            return new Route(className, action);
        }


    }
}
