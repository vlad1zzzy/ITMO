package ru.itmo.web.lesson4.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    /*    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            File file = new File(getServletContext().getRealPath(request.getRequestURI()));
            if (file.isFile()) {
                response.setContentType(getServletContext().getMimeType(file.getCanonicalPath()));
                Files.copy(file.toPath(), response.getOutputStream());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String firstUri = uri.split("\\+")[0];
        for (String parsedUri : uri.split("\\+")) {
            File file = new File("C:/Users/vlad/Desktop/lesson4/src/main/webapp", parsedUri);
            if (!file.isFile())
                file = new File(getServletContext().getRealPath(parsedUri));
            if (file.isFile()) {
                response.setContentType(getContentTypeFromName(firstUri));
                OutputStream outputStream = response.getOutputStream();
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
