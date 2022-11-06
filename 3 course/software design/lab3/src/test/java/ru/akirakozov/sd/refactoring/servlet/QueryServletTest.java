package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QueryServletTest {
    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private final String mockBaseName = "test";

    private final long mockBasePrice = 666;

    private final long mockBaseSum = 9999L;

    private final int mockBaseCount = 10;

    private final Product mockBaseProduct = new Product(mockBaseName, mockBasePrice);

    private AbstractProductServlet servlet;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new QueryServlet(productDao);
    }

    @Test
    public void max() throws IOException, SQLException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        final String format = "<html><body><h1>Product with max price: </h1>%s\t%d</br></body></html>";

        when(servletRequest.getParameter("command")).thenReturn("max");
        when(productDao.findMaxPriceProduct()).thenReturn(Optional.of(mockBaseProduct));
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        assertThat(stringWriter.toString())
                .isEqualToIgnoringNewLines(String.format(format, mockBaseName, mockBasePrice));

        printer.flush();
    }

    @Test
    public void min() throws IOException, SQLException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        final String format = "<html><body><h1>Product with min price: </h1>%s\t%d</br></body></html>";

        when(servletRequest.getParameter("command")).thenReturn("min");
        when(productDao.findMinPriceProduct()).thenReturn(Optional.of(mockBaseProduct));
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        assertThat(stringWriter.toString())
                .isEqualToIgnoringNewLines(String.format(format, mockBaseName, mockBasePrice));

        printer.flush();
    }

    @Test
    public void sum() throws IOException, SQLException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        final String format = "<html><body>Summary price: %d</body></html>";

        when(servletRequest.getParameter("command")).thenReturn("sum");
        when(productDao.getPricesSum()).thenReturn(mockBaseSum);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(String.format(format, mockBaseSum));

        printer.flush();
    }

    @Test
    public void count() throws IOException, SQLException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        final String format = "<html><body>Number of products: %d</body></html>";

        when(servletRequest.getParameter("command")).thenReturn("count");
        when(productDao.getProductsCount()).thenReturn(mockBaseCount);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(String.format(format, mockBaseCount));
    }

}