package ru.akirakozov.sd.refactoring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilsDao {
    private static final String DB_URL = "jdbc:sqlite:test.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTable() throws SQLException {
        common("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public static void cleanTable() throws SQLException {
        common("DELETE FROM PRODUCT");
    }

    private static void common(final String sql) throws SQLException {
        try (final Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
}