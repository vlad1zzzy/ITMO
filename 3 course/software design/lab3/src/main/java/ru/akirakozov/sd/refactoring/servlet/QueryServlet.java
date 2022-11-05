package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.printer.Html.printInfo;
import static ru.akirakozov.sd.refactoring.printer.Html.printProduct;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final ProductDao productDao;

    public QueryServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                Optional<Product> maxPriceProduct = productDao.findMaxPriceProduct();
                printProduct(maxPriceProduct,"Product with max price: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                Optional<Product> minPriceProduct = productDao.findMinPriceProduct();
                printProduct(minPriceProduct,"Product with min price: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                long summaryPrice = productDao.getPricesSum();
                printInfo(summaryPrice,"Summary price: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                int count = productDao.getProductsCount();
                printInfo(count,"Number of products: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}