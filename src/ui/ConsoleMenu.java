package ui;

import exception.InsufficientStockException;
import exception.InvalidInputException;
import exception.ProductNotFoundException;
import model.*;
import service.InventoryService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final InventoryService service = new InventoryService();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   SHOP INVENTORY MANAGEMENT SYSTEM    ║");
        System.out.println("╚════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addProductMenu();
                case 2 -> viewAllProducts();
                case 3 -> searchMenu();
                case 4 -> updateProduct();
                case 5 -> deleteProduct();
                case 6 -> sellProduct();
                case 7 -> service.generateReport();
                case 8 -> {
                    int t = readInt("Enter low-stock threshold quantity: ");
                    service.generateLowStockReport(t);
                }
                case 0 -> {
                    System.out.println("Thank you! Goodbye.");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n──────────── MAIN MENU ────────────");
        System.out.println(" 1. Add Product");
        System.out.println(" 2. View All Products");
        System.out.println(" 3. Search Product");
        System.out.println(" 4. Update Product");
        System.out.println(" 5. Delete Product");
        System.out.println(" 6. Sell Product (Reduce Stock)");
        System.out.println(" 7. Full Inventory Report");
        System.out.println(" 8. Low Stock Report");
        System.out.println(" 0. Exit");
        System.out.println("────────────────────────────────────");
    }

    private void addProductMenu() {
        System.out.println("\n--- Select Product Type ---");
        System.out.println("1. Electronics");
        System.out.println("2. Grocery");
        System.out.println("3. Clothing");
        int type = readInt("Choice: ");

        System.out.print("Product Name: "); String name = sc.nextLine().trim();
        double price = readDouble("Price (Rs.): ");
        int qty = readInt("Quantity: ");

        try {
            Product product = switch (type) {
                case 1 -> {
                    System.out.print("Brand: "); String brand = sc.nextLine().trim();
                    int warranty = readInt("Warranty (months): ");
                    yield new ElectronicsProduct(0, name, price, qty, brand, warranty);
                }
                case 2 -> {
                    System.out.print("Expiry Date (DD-MM-YYYY): "); String expiry = sc.nextLine().trim();
                    int org = readInt("Is Organic? (1=Yes, 0=No): ");
                    yield new GroceryProduct(0, name, price, qty, expiry, org == 1);
                }
                case 3 -> {
                    System.out.print("Size (S/M/L/XL): "); String size = sc.nextLine().trim();
                    System.out.print("Color: "); String color = sc.nextLine().trim();
                    yield new ClothingProduct(0, name, price, qty, size, color);
                }
                default -> throw new InvalidInputException("Invalid product type selected.");
            };
            service.addProduct(product);
        } catch (InvalidInputException e) {
            System.out.println("Input Error: " + e.getMessage());
        }
    }

    private void viewAllProducts() {
        List<Product> list = service.getAllProducts();
        System.out.println("\n========== ALL PRODUCTS ==========");
        if (list.isEmpty()) { System.out.println("No products in inventory."); return; }
        for (Product p : list) System.out.println(p);
        System.out.println("Total: " + list.size() + " product(s).");
    }

    private void searchMenu() {
        System.out.println("\n1. Search by Name   2. Search by Category");
        int ch = readInt("Choice: ");
        List<Product> results;
        if (ch == 1) {
            System.out.print("Enter product name: "); String n = sc.nextLine().trim();
            results = service.searchByName(n);
        } else {
            System.out.println("Categories: Electronics | Grocery | Clothing");
            System.out.print("Enter category: "); String c = sc.nextLine().trim();
            results = service.searchByCategory(c);
        }
        if (results.isEmpty()) System.out.println("No products found.");
        else results.forEach(System.out::println);
    }

    private void updateProduct() {
        int id = readInt("Enter Product ID to update: ");
        System.out.print("New Name: "); String name = sc.nextLine().trim();
        double price = readDouble("New Price: ");
        int qty = readInt("New Quantity: ");
        try {
            service.updateProduct(id, name, price, qty);
        } catch (ProductNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        int id = readInt("Enter Product ID to delete: ");
        try {
            service.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void sellProduct() {
        int id = readInt("Enter Product ID to sell: ");
        int qty = readInt("Quantity to sell: ");
        try {
            service.sellProduct(id, qty);
        } catch (ProductNotFoundException | InsufficientStockException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─── Input helpers ───
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = sc.nextInt(); sc.nextLine(); return val;
            } catch (InputMismatchException e) {
                sc.nextLine(); System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = sc.nextDouble(); sc.nextLine(); return val;
            } catch (InputMismatchException e) {
                sc.nextLine(); System.out.println("Please enter a valid number.");
            }
        }
    }
}
