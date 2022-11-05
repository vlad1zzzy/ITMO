package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.akirakozov.sd.refactoring.dao.UtilsDao.getConnection;

public class ProductDao {
    public void insert(Product product) throws SQLException {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES " +
                "(\"" + product.getName() + "\"," + product.getPrice() + ")";
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate(sql);
    }

    public List<Product> getProducts() throws SQLException {
        String sql = "SELECT * FROM PRODUCT";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return parseProducts(rs);
    }

    public Optional<Product> findMaxPriceProduct() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return parseProducts(rs).stream().findFirst();
    }

    public Optional<Product> findMinPriceProduct() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return parseProducts(rs).stream().findFirst();
    }

    public long getPricesSum() throws SQLException {
        String sql = "SELECT SUM(price) as sum FROM PRODUCT";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs.getLong("sum");
    }

    public int getProductsCount() throws SQLException {
        String sql = "SELECT COUNT(*) as cnt FROM PRODUCT";
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs.getInt("cnt");
    }

    private List<Product> parseProducts(ResultSet rs) throws SQLException {
        List<Product> result = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            result.add(new Product(name, price));
        }
        return result;
    }
}