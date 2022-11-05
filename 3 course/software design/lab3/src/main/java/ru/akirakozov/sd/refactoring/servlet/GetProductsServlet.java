package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.akirakozov.sd.refactoring.printer.Html.printProducts;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {
    public GetProductsServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        printProducts(productDao.getProducts(), response.getWriter());
    }
}