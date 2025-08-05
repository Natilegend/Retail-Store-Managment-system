package util;

import Exception.RetailInventoryException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseUtil {

    public static Connection getConnection() throws RetailInventoryException {
        try {
            return DriverManager.getConnection("jdbc:sqlite:inventory.db");
        } catch (SQLException e) {
            throw new RetailInventoryException("Database connection error: " + e.getMessage());
        }
    }

    public static void initializeDatabase() {
        String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                                       "id INTEGER PRIMARY KEY, " +
                                       "name TEXT NOT NULL UNIQUE" +
                                       ");";

        String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                                     "id INTEGER PRIMARY KEY, " +
                                     "name TEXT NOT NULL, " +
                                     "price REAL NOT NULL, " +
                                     "quantity INTEGER NOT NULL, " +
                                     "category_id INTEGER, " +
                                     "FOREIGN KEY (category_id) REFERENCES categories(id)" +
                                     ");";

        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(createCategoriesTable);
            stmt.execute(createProductsTable);
        } catch (SQLException | RetailInventoryException e) {
            throw new RuntimeException("Error initializing database schema", e);
        }
    }
}

