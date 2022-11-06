package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static ru.akirakozov.sd.refactoring.dao.UtilsDao.getConnection;

public class ProductDao {
    public void insert(final Product product) throws SQLException {
        try (final Statement stmt = getConnection().createStatement()) {
            final String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES " +
                    "(\"" + product.getName() + "\"," + product.getPrice() + ")";

            stmt.executeUpdate(sql);
        }
    }

    public List<Product> getProducts() throws SQLException {
        return getQuery("SELECT * FROM PRODUCT", ProductDao::parseProducts);
    }

    public Optional<Product> findMaxPriceProduct() throws SQLException {
        return getQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", minmax);
    }

    public Optional<Product> findMinPriceProduct() throws SQLException {
        return getQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", minmax);
    }

    public long getPricesSum() throws SQLException {
        return getQuery("SELECT SUM(price) as sum FROM PRODUCT", sum);
    }

    public int getProductsCount() throws SQLException {
        return getQuery("SELECT COUNT(*) as cnt FROM PRODUCT", count);
    }

    public <T> T getQuery(final String sql, final Function<ResultSet, T> parseFunction) throws SQLException {
        try (final Statement stmt = getConnection().createStatement()) {
            return parseFunction.apply(stmt.executeQuery(sql));
        }
    }

    private static List<Product> parseProducts(final ResultSet rs) {
        final List<Product> result = new ArrayList<>();
        try {
            while (rs.next()) {
                final String name = rs.getString("name");
                final int price = rs.getInt("price");
                result.add(new Product(name, price));
            }
        } catch (final SQLException ignored) {
        }

        return result;
    }

    private final Function<ResultSet, Optional<Product>> minmax = (ResultSet t) -> parseProducts(t).stream().findFirst();

    private final Function<ResultSet, Long> sum = (ResultSet t) -> {
        try {
            return t.getLong("sum");
        } catch (SQLException ignored) {
        }
        return 0L;
    };

    private final Function<ResultSet, Integer> count = (ResultSet t) -> {
        try {
            return t.getInt("cnt");
        } catch (SQLException ignored) {
        }
        return 0;
    };
}