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
        final String format = "<html><body><h1>Product with max price: </h1>%s\t%d</br></body></html>";
        final String expected = String.format(format, mockBaseName, mockBasePrice);

        when(productDao.findMaxPriceProduct()).thenReturn(Optional.of(mockBaseProduct));

        base("max", expected);
    }

    @Test
    public void min() throws IOException, SQLException {
        final String format = "<html><body><h1>Product with min price: </h1>%s\t%d</br></body></html>";
        final String expected = String.format(format, mockBaseName, mockBasePrice);

        when(productDao.findMinPriceProduct()).thenReturn(Optional.of(mockBaseProduct));

        base("min", expected);
    }

    @Test
    public void sum() throws IOException, SQLException {
        final String format = "<html><body>Summary price: %d</body></html>";
        final String expected = String.format(format, mockBaseSum);

        when(productDao.getPricesSum()).thenReturn(mockBaseSum);

        base("sum", expected);
    }

    @Test
    public void count() throws IOException, SQLException {
        final String format = "<html><body>Number of products: %d</body></html>";
        final String expected = String.format(format, mockBaseCount);

        when(productDao.getProductsCount()).thenReturn(mockBaseCount);

        base("count", expected);
    }

    private void base(final String command, final String expected) throws IOException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);

        when(servletRequest.getParameter("command")).thenReturn(command);
        when(servletResponse.getWriter()).thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(expected);

        printer.flush();
    }
}