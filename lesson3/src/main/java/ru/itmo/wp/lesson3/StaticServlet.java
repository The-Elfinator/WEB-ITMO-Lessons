package ru.itmo.wp.lesson3;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        //write code below super.init()
//
//
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File(getServletContext().getRealPath("/static"), request.getRequestURI());
        if (file.isFile()) {
            response.setContentType(getServletContext().getMimeType(file.getAbsolutePath()));
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                Files.copy(file.toPath(), outputStream);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        System.out.println(request);
    }

//    @Override
//    public void destroy() {
//        //write code above super.destroy()
//
//
//        super.destroy();
//    }
}
