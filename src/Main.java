package src;

import src.model.Category;
import src.model.Product;
import src.service.InventoryService;
import src.Exception.RetailInventoryException;

import java.util.Scanner;

public class Main {
    private static final InventoryService service = new InventoryService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Initialize the database and create tables if they don't exist
            src.util.DatabaseUtil.initializeDatabase();
        } catch (RuntimeException e) {
            System.out.println("Fatal Error: Could not initialize the database.");
            e.printStackTrace();
            return; // Exit if the database can't be set up
        }

        while (true) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> updateProduct();
                    case 3 -> deleteProduct();
                    case 4 -> listProducts();
                    case 5 -> addCategory();
                    case 6 -> updateCategory();
                    case 7 -> deleteCategory();
                    case 8 -> listCategories();
                    case 9 -> System.exit(0);
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (RetailInventoryException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Press Enter to continue...");
                scanner.nextLine(); // This helps pause the loop
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Retail Store Inventory Management System ===");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. List All Products");
        System.out.println("5. Add Category");
        System.out.println("6. Update Category");
        System.out.println("7. Delete Category");
        System.out.println("8. List All Categories");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addProduct() throws RetailInventoryException {
        System.out.print("Enter product ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter product quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter category ID: ");
        int catId = Integer.parseInt(scanner.nextLine());
        Category category = service.findCategoryById(catId);
        if (category == null) {
            System.out.println("Category not found!");
            return;
        }
        Product product = new Product(id, name, price, quantity, category);
        service.addProduct(product);
        System.out.println("Product added successfully!");
    }

    private static void updateProduct() throws RetailInventoryException {
        System.out.print("Enter product ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        Product product = service.findProductById(id);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        System.out.print("Enter new name (current: " + product.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) product.setName(name);
        System.out.print("Enter new price (current: " + product.getPrice() + "): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) product.setPrice(Double.parseDouble(priceInput));
        System.out.print("Enter new quantity (current: " + product.getQuantity() + "): ");
        String qtyInput = scanner.nextLine();
        if (!qtyInput.isEmpty()) product.setQuantity(Integer.parseInt(qtyInput));
        System.out.print("Enter new category ID (current: " + product.getCategory().getId() + "): ");
        String catIdInput = scanner.nextLine();
        if (!catIdInput.isEmpty()) {
            Category category = service.findCategoryById(Integer.parseInt(catIdInput));
            if (category == null) {
                System.out.println("Category not found!");
                return;
            }
            product.setCategory(category);
        }
        service.updateProduct(product);
        System.out.println("Product updated successfully!");
    }

    private static void deleteProduct() throws RetailInventoryException {
        System.out.print("Enter product ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        service.deleteProduct(id);
        System.out.println("Product deleted successfully!");
    }

    private static void listProducts() throws RetailInventoryException {
        System.out.println("\n=== Products ===");
        for (Product product : service.getAllProducts()) {
            System.out.println(product);
        }
    }

    private static void addCategory() throws RetailInventoryException {
        System.out.print("Enter category ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        Category category = new Category(name, id);
        service.addCategory(category);
        System.out.println("Category added successfully!");
    }

    private static void updateCategory() throws RetailInventoryException {
        System.out.print("Enter category ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        Category category = service.findCategoryById(id);
        if (category == null) {
            System.out.println("Category not found!");
            return;
        }
        System.out.print("Enter new name (current: " + category.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) category.setName(name);
        service.updateCategory(category);
        System.out.println("Category updated successfully!");
    }

    private static void deleteCategory() throws RetailInventoryException {
        System.out.print("Enter category ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        service.deleteCategory(id);
        System.out.println("Category deleted successfully!");
    }

    private static void listCategories() throws RetailInventoryException {
        System.out.println("\n=== Categories ===");
        for (Category category : service.getAllCategories()) {
            System.out.println(category);
        }
    }
}