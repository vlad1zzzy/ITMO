package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddProductServletTest {
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
        servlet = new AddProductServlet(productDao);
    }

    @Test
    public void test() throws IOException, SQLException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        final String mockName = "test";
        final long mockPrice = 777;
        final Product expected = new Product(mockName, mockPrice);

        when(servletResponse.getWriter()).thenReturn(printer);
        when(servletRequest.getParameter("name")).thenReturn(mockName);
        when(servletRequest.getParameter("price")).thenReturn(String.valueOf(mockPrice));

        servlet.doGet(servletRequest, servletResponse);

        final ArgumentCaptor<Product> product = ArgumentCaptor.forClass(Product.class);
        verify(productDao).insert(product.capture());

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines("OK");
        assertThat(product.getValue()).isEqualTo(expected);

        printer.flush();
    }
}