package src.service;

import src.dao.ProductDAO;
import src.dao.CategoryDAO;
import src.model.Product;
import src.model.Category;
import src.util.FileUtil;
import src.Exception.RetailInventoryException;

import java.util.List;
import java.util.Set;

public class InventoryService {
    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    public void addProduct(Product product) throws RetailInventoryException {
        productDAO.create(product);
        FileUtil.log("Added product: " + product);
    }

    public void updateProduct(Product product) throws RetailInventoryException {
        productDAO.update(product);
        FileUtil.log("Updated product: " + product);
    }

    public void deleteProduct(int id) throws RetailInventoryException {
        productDAO.delete(id);
        FileUtil.log("Deleted product with id: " + id);
    }

    public Product findProductById(int id) throws RetailInventoryException {
        return productDAO.findById(id);
    }

    public List<Product> getAllProducts() throws RetailInventoryException {
        return productDAO.findAll();
    }

    public void addCategory(Category category) throws RetailInventoryException {
        categoryDAO.create(category);
        FileUtil.log("Added category: " + category);
    }

    public void updateCategory(Category category) throws RetailInventoryException {
        categoryDAO.update(category);
        FileUtil.log("Updated category: " + category);
    }

    public void deleteCategory(int id) throws RetailInventoryException {
        categoryDAO.delete(id);
        FileUtil.log("Deleted category with id: " + id);
    }

    public Category findCategoryById(int id) throws RetailInventoryException {
        return categoryDAO.findById(id);
    }

    public Set<Category> getAllCategories() throws RetailInventoryException {
        return categoryDAO.findAll();
    }

    public void addProduct(String name, double price, int quantity, Category category) throws RetailInventoryException {
        int id = generateProductId();
        Product product = new Product(id, name, price, quantity, category);
        addProduct(product);
    }

    private int generateProductId() throws RetailInventoryException {
        List<Product> products = getAllProducts();
        return products.isEmpty() ? 1 : products.get(products.size() - 1).getId() + 1;
    }
}