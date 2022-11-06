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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProductsServletTest {
    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AbstractProductServlet servlet;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        servlet = new GetProductsServlet(productDao);
    }

    @Test
    public void test() throws IOException, SQLException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        final List<Product> mock = Arrays.asList(
                new Product("test1", 666),
                new Product("test2", 777),
                new Product("test3", 888)
        );
        final String htmlBody = mock
                .stream()
                .map(p -> String.format("%s\t%d</br>", p.getName(), p.getPrice()))
                .collect(Collectors.joining());
        final String htmlFormat = "<html><body>%s</body></html>";
        final String expected = String.format(htmlFormat, htmlBody);

        when(servletResponse.getWriter()).thenReturn(printer);
        when(productDao.getProducts()).thenReturn(mock);

        servlet.doGet(servletRequest, servletResponse);

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(expected);

        printer.flush();
    }

}