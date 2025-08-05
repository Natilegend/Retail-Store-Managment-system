package model;

public class Category extends InventoryItem {
    public Category(String name, int id) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Category{id=" + getId() + ", name=" + getName() + '}';
    }
}
