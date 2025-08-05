# Retail Store Inventory Management System

## Project Overview
This is a CLI-based Java application for managing a retail store's inventory. It allows users to manage products and categories, with data persisted in a SQLite database and activities logged to a text file.

## Setup Instructions
1. Ensure Java 21 is installed.
2. Place the SQLite JDBC driver JAR in the `lib` folder.
3. Compile the source code using
    ```sh
       javac -cp ".;lib/sqlite-jdbc-3.50.1.0.jar" -d bin src/Main.java src/util/DatabaseUtil.java src/util/FileUtil.java src/model/*.java src/dao/*.java src/service/*.java src/Exception/*.java
    ```
4. Run the application
    ```sh
      java -cp ".;lib/sqlite-jdbc-3.50.1.0.jar;bin" Main
    ```
---
## Project Structure

```plaintext
Retail-Store-Managment-system/
│
├── lib/
│   └── sqlite-jdbc-3.50.1.0.jar         # SQLite JDBC driver
│
├── src/
│   ├── Main.java                        # Main entry point
│   ├── dao/                             # Data Access Objects
│   │   ├── CategoryDAO.java
│   │   ├── DataAccessObject.java
│   │   └── ProductDAO.java
│   ├── Exception/                         #Custom exception
│   │   └── RetailInventoryException.java
│   ├── model/                           # Data models
│   │   ├── Category.java
│   │   ├── InventoryItem.java
│   │   └── Product.java
│   ├── service/
│   │   └── InventoryService.java
│   └── util/
│       ├── DatabaseUtil.java
│       └── FileUtil.java
│
├── README.md                            # Project documentation
├── Retail-Store-Managment-system.iml     # IDE project file
```
---

## How It Works

This Retail Store Management System is a simple command-line application for managing products and categories in a retail store. It uses a SQLite database to store all data persistently.

### Main Features

- Add, update, delete, and list products
- Add, update, delete, and list categories
- Each product belongs to a category
- All actions are logged to a file for auditing

### Application Flow

1. **Startup**
    - When the application starts, it initializes the SQLite database (`inventory.db`) and creates the necessary tables (`categories` and `products`) if they do not exist.

2. **Main Menu**
    - The user is presented with a menu of options:
        - Add Product
        - Update Product
        - Delete Product
        - List All Products
        - Add Category
        - Update Category
        - Delete Category
        - List All Categories
        - Exit

3. **User Actions**
    - The user selects an option by entering the corresponding number.
    - For actions like adding or updating, the user is prompted to enter details (e.g., product name, price, category ID).
    - The application validates input and performs the requested operation using the `InventoryService` class.

4. **Database Operations**
    - All data operations (add, update, delete, list) are handled through service and DAO (Data Access Object) classes, which interact with the SQLite database.
    - Products reference categories via a foreign key (`category_id`).

5. **Logging**
    - Every significant action (such as adding, updating, or deleting a product or category) is logged to `logs/activity.txt` for record-keeping.

6. **Error Handling**
    - The application handles invalid input and database errors gracefully, displaying user-friendly error messages.

### Example Usage

- **Add a Product:**  
  Enter product details (ID, name, price, quantity, category ID). The product is saved to the database and the action is logged.
- **List All Products:**  
  Displays all products with their details, including category information.
- **Delete a Category:**  
  Removes a category from the database (products referencing this category should be handled appropriately).

---
## Database Structure

The application uses a SQLite database (`inventory.db`) with the following tables:

### categories
| Column | Type    | Description                |
|--------|---------|----------------------------|
| id     | INTEGER | Primary key, unique ID     |
| name   | TEXT    | Category name, unique      |

### products
| Column      | Type    | Description                          |
|-------------|---------|--------------------------------------|
| id          | INTEGER | Primary key, unique ID               |
| name        | TEXT    | Product name                         |
| price       | REAL    | Product price                        |
| quantity    | INTEGER | Product quantity in stock            |
| category_id | INTEGER | Foreign key to categories(id)        |

---


## How It Works

This Retail Store Management System is a simple command-line application for managing products and categories in a retail store. It uses a SQLite database to store all data persistently.

### Main Features

- Add, update, delete, and list products
- Add, update, delete, and list categories
- Each product belongs to a category
- All actions are logged to a file for auditing

---

## Technologies Used

- Java (main application logic)
- SQLite (database)
- JDBC (database connectivity)
- File I/O (for logging)





