package ru.itmo.wp.lesson3;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class LoggingFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("[" + new Date() + "]: Request processing started [url=" + request.getRequestURI() + "]");
        super.doFilter(request, response, chain);
        System.out.println("[" + new Date() + "]: Request processing finished [url=" + request.getRequestURI() + "]");
    }
}
