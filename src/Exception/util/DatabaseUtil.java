package src.Exception.util;

import src.Exception.RetailInventoryException;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try {
            Class.forName("org.sqlite.JDBC");

            Properties prop = new Properties();
            try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("config/dbconfig.properties")) {

                if (input == null) {
                    throw new RuntimeException("Cannot find dbconfig.properties in the 'config' directory.");
                }

                prop.load(input);
                URL = prop.getProperty("db.url");
                USER = prop.getProperty("db.user");
                PASSWORD = prop.getProperty("db.password");

            } catch (IOException ex) {
                throw new RuntimeException("Error loading database configuration", ex);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC Driver not found. Please ensure the 'sqlite-jdbc.jar' file is in your project's classpath.", e);
        }
    }

    public static Connection getConnection() throws RetailInventoryException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
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

