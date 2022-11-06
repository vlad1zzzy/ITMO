package ru.akirakozov.sd.refactoring.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.akirakozov.sd.refactoring.dao.UtilsDao.cleanTable;
import static ru.akirakozov.sd.refactoring.dao.UtilsDao.createTable;

public class ProductDaoTest {
    private static final List<Product> mock = Arrays.asList(
            new Product("test1", 1),
            new Product("test2", 2),
            new Product("test3", 300000000)
    );
    private static final ProductDao productDao = new ProductDao();

    @Before
    public void init() throws SQLException {
        createTable();
    }

    @After
    public void clean() throws Exception {
        cleanTable();
    }

    private void insertProducts() throws SQLException {
        for (final Product product : ProductDaoTest.mock) {
            productDao.insert(product);
        }
    }

    @Test
    public void empty() throws SQLException {
        assertThat(productDao.getProducts()).isEmpty();
    }

    @Test
    public void emptyMax() throws SQLException {
        assertThat(productDao.findMinPriceProduct()).isNotPresent();
    }

    @Test
    public void emptyMin() throws SQLException {
        assertThat(productDao.findMinPriceProduct()).isNotPresent();
    }

    @Test
    public void emptySum() throws SQLException {
        assertThat(productDao.getPricesSum()).isEqualTo(0L);
    }

    @Test
    public void emptyCount() throws SQLException {
        assertThat(productDao.getProductsCount()).isEqualTo(0);
    }

    @Test
    public void insert() throws SQLException {
        insertProducts();
        assertThat(productDao.getProducts()).contains(mock.toArray(new Product[3]));
    }

    @Test
    public void max() throws SQLException {
        insertProducts();
        final Optional<Product> product = productDao.findMaxPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(mock.get(2));
    }

    @Test
    public void min() throws SQLException {
        insertProducts();
        final Optional<Product> product = productDao.findMinPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(mock.get(0));
    }

    @Test
    public void sum() throws SQLException {
        insertProducts();
        assertThat(productDao.getPricesSum()).isEqualTo(300000003L);
    }

    @Test
    public void count() throws SQLException {
        insertProducts();
        assertThat(productDao.getProductsCount()).isEqualTo(3);
    }
}