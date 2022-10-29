package ru.itmo.web.lesson4.servlet;

import freemarker.template.*;
import ru.itmo.web.lesson4.util.DataUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerServlet extends HttpServlet {
    private final Configuration templateConfiguration = new Configuration(Configuration.VERSION_2_3_31);
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    @Override
    public void init() throws ServletException {
        File templatesDir = new File(getServletContext().getRealPath("."),
                "../../src/main/webapp/WEB-INF/templates"
        );
        try {
            templateConfiguration.setDirectoryForTemplateLoading(templatesDir);
        } catch (IOException e) {
            throw new ServletException("Can't set templates directory '" + templatesDir + "'.", e);
        }

        templateConfiguration.setDefaultEncoding(UTF_8);
        templateConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        templateConfiguration.setLogTemplateExceptions(false);
        templateConfiguration.setWrapUncheckedExceptions(true);
        templateConfiguration.setFallbackOnNullLoopVariable(false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);
        Template template;
        try {
            template = templateConfiguration.getTemplate(
                    URLDecoder.decode(request.getRequestURI(), UTF_8) + ".ftlh"
            );
        } catch (TemplateNotFoundException ignored) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType("text/html");

        Map<String, Object> data = getData(request);

        try {
            template.process(data, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace(System.err);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }
    }

    private Map<String, Object> getData(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();

        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            if (e.getValue() != null && e.getValue().length == 1) {
                data.put(e.getKey(), e.getValue()[0]);
            }
        }
        
        DataUtil.addData(data);
        return data;
    }
}
