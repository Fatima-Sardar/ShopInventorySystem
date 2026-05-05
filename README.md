# Shop Inventory Management System

A Java-based console application for managing a shop's inventory. The system allows shop owners to add, update, delete, search, and sell products with full MySQL database integration.

---

## Group Members

| Full Name | CMS / ID   | Section |
|-----------|------------|---------|
| Fatima    | 023-25-0208|    B    |

---

## Project Purpose

Small shop owners often struggle to track stock manually. This system solves that by providing a structured, OOP-based inventory management solution where products can be tracked by category, stock levels monitored, and sales recorded in real time.

---

## OOP Features Used

| Concept | Where Applied |
|---|---|
| **Abstraction** | `Product` is an abstract class with abstract `calculateDiscount()` |
| **Inheritance** | `ElectronicsProduct`, `GroceryProduct`, `ClothingProduct` extend `Product` |
| **Polymorphism** | All product types stored as `Product`, `calculateDiscount()` behaves differently per type |
| **Encapsulation** | All fields are `private` with validated getters/setters |
| **Interfaces** | `Searchable` and `Reportable` implemented by `InventoryService` |
| **Exception Handling** | Custom exceptions: `ProductNotFoundException`, `InsufficientStockException`, `InvalidInputException` |
| **Collections** | `ArrayList<Product>` used to hold and display query results |
| **Database** | MySQL via JDBC with `PreparedStatement` |

---

## Main Modules

```
ShopInventorySystem/
├── src/
│   ├── Main.java                        → Entry point of the program
│   ├── db/
│   │   └── DatabaseConnection.java      → MySQL database connection (Singleton)
│   ├── model/
│   │   ├── Product.java                 → Abstract base class
│   │   ├── ElectronicsProduct.java      → Electronics subclass (10% discount)
│   │   ├── GroceryProduct.java          → Grocery subclass (5% organic discount)
│   │   ├── ClothingProduct.java         → Clothing subclass (15% discount)
│   │   ├── Searchable.java              → Interface for search operations
│   │   └── Reportable.java             → Interface for report generation
│   ├── service/
│   │   └── InventoryService.java        → Business logic (implements Searchable, Reportable)
│   ├── exception/
│   │   ├── ProductNotFoundException.java
│   │   ├── InsufficientStockException.java
│   │   └── InvalidInputException.java
│   └── ui/
│       └── ConsoleMenu.java             → Console menu / user interface
├── shop_inventory.sql                   → MySQL database script
└── README.md
```

---

## How to Run

### Requirements
- JDK 17 or above
- MySQL Server 8.0
- MySQL Connector/J JAR (`mysql-connector-j-9.7.0.jar`)

---

### Step 1 — Setup Database

Open CMD and connect to MySQL:
```
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```

Then run the SQL file:
```sql
source C:/path/to/ShopInventorySystem/shop_inventory.sql;
```

---

### Step 2 — Configure Password

Open `src/db/DatabaseConnection.java` and update your MySQL password:
```java
private static final String PASSWORD = "your_mysql_password";
```

---

### Step 3 — Compile

Open CMD and go to the src folder:
```
cd C:\Users\HP\Desktop\ShopInventorySystem\src
```

Then compile:
```
javac -cp ".;C:\Users\HP\Desktop\mysql-connector-j-9.7.0\mysql-connector-j-9.7.0.jar" -d C:\Users\HP\Desktop\ShopInventorySystem\out db\DatabaseConnection.java exception\*.java model\*.java service\InventoryService.java ui\ConsoleMenu.java Main.java
```

---

### Step 4 — Run

```
java -cp ".;C:\Users\HP\Desktop\ShopInventorySystem\out;C:\Users\HP\Desktop\mysql-connector-j-9.7.0\mysql-connector-j-9.7.0.jar" Main
```

---

## Features

- Add Electronics, Grocery, or Clothing products
- View all products with discounted prices
- Search by name or category
- Update product details
- Delete product
- Sell product (automatically reduces stock)
- Full inventory report with total inventory value
- Low stock alert report

---

## Demo Video
[YouTube Link — Add after recording]

## GitHub Repository
[GitHub URL — Add after uploading]
