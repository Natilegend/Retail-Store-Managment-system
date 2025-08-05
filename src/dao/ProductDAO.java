package dao;

import model.Product;
import model.Category;
import util.DatabaseUtil;
import Exception.RetailInventoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DataAccessObject<Product> {

    @Override
    public void create(Product product) throws RetailInventoryException {
        String sql = "INSERT INTO products (id, name, price, quantity, category_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getCategory().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RetailInventoryException("Error creating product: " + e.getMessage());
        }
    }

    @Override
    public Product findById(int id) throws RetailInventoryException {
        String sql = "SELECT p.*, c.id as cat_id, c.name as cat_name FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category category = new Category(rs.getString("cat_name"), rs.getInt("cat_id"));
                return new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), category);
            }
            return null;
        } catch (SQLException e) {
            throw new RetailInventoryException("Error finding product: " + e.getMessage());
        }
    }

    @Override
    public void update(Product product) throws RetailInventoryException {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getCategory().getId());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RetailInventoryException("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws RetailInventoryException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RetailInventoryException("Error deleting product: " + e.getMessage());
        }
    }

    public List<Product> findAll() throws RetailInventoryException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.id as cat_id, c.name as cat_name FROM products p JOIN categories c ON p.category_id = c.id";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Category category = new Category(rs.getString("cat_name"), rs.getInt("cat_id"));
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), category));
            }
            return products;
        } catch (SQLException e) {
            throw new RetailInventoryException("Error retrieving products: " + e.getMessage());
        }
    }
}