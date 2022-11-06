package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractProductServlet extends HttpServlet {
    protected final ProductDao productDao;

    public AbstractProductServlet(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            doRequest(request, response);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected abstract void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;

}