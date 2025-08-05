package model;

public class Product extends InventoryItem {
    private double price;
    private int quantity;
    private Category category;

    public Product(int id, String name, double price, int quantity, Category category) {
        super(id, name);
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{id=" + getId() + ", name='" + getName() + "', price=" + price + ", quantity=" + quantity + ", category=" + category.getName() + "}";
    }
}