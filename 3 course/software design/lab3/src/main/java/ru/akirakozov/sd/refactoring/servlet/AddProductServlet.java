package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractProductServlet {
    public AddProductServlet(final ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String name = request.getParameter("name");
        final long price = Long.parseLong(request.getParameter("price"));

        productDao.insert(new Product(name, price));

        response.getWriter().println("OK");
    }
}