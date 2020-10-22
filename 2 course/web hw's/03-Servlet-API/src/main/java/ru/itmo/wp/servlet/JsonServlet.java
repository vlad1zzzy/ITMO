package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class JsonServlet extends HttpServlet {

    //private static final ArrayList<HashMap<String, String>> messages = new ArrayList<>();
    private static final List<HashMap<String, String>> messages = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        response.setContentType("application/json");
        switch (uri) {
            case "/message/auth":
                doPostAuth(request, response);
                break;
            case "/message/findAll":
                doPostFindAll(request, response);
                break;
            case "/message/add":
                doPostAdd(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPostAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //default name
        String user = "";
        //if already signed up
        if (getSessionName(request) != null) {
            user = getSessionName(request).toString();
        }
        //if have new name
        else if (request.getParameter("user") != null) {
            user = request.getParameter("user");
            request.getSession().setAttribute("name", request.getParameter("user"));
        }
        String json = new Gson().toJson(user);
        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
    }

    protected void doPostFindAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(messages);
        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
    }

    protected void doPostAdd(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> message = new HashMap<>();
        message.put("user", getSessionName(request).toString());
        message.put("text", request.getParameter("text"));
        messages.add(message);
    }

    protected Object getSessionName(HttpServletRequest request) {
        return request.getSession().getAttribute("name");
    }
}
