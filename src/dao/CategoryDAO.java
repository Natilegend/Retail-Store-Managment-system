package src.dao;

import src.model.Category;
import src.util.DatabaseUtil;
import src.Exception.RetailInventoryException;

import java.util.HashSet;
import java.util.Set;
import java.sql.*;

public class CategoryDAO implements DataAccessObject<Category> {

    @Override
    public void create(Category category) throws RetailInventoryException {
        String sql = "INSERT INTO categories (id, name) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, category.getId());
            stmt.setString(2, category.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RetailInventoryException("Error creating category: " + e.getMessage());
        }
    }

    @Override
    public Category findById(int id) throws RetailInventoryException {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getString("name"), rs.getInt("id"));
            }
            return null;
        } catch (SQLException e) {
            throw new RetailInventoryException("Error finding category: " + e.getMessage());
        }
    }

    @Override
    public void update(Category category) throws RetailInventoryException {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RetailInventoryException("Error updating category: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws RetailInventoryException {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RetailInventoryException("Error deleting category: " + e.getMessage());
        }
    }

    public Set<Category> findAll() throws RetailInventoryException {
        Set<Category> categories = new HashSet<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(new Category(rs.getString("name"), rs.getInt("id")));
            }
            return categories;
        } catch (SQLException e) {
            throw new RetailInventoryException("Error retrieving categories: " + e.getMessage());
        }
    }
}
  
