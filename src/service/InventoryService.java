package service;

import db.DatabaseConnection;
import exception.InsufficientStockException;
import exception.InvalidInputException;
import exception.ProductNotFoundException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Service class implementing Searchable and Reportable interfaces
public class InventoryService implements Searchable, Reportable {

    // ─────────────────────────────────────────────
    // ADD PRODUCT
    // ─────────────────────────────────────────────
    public void addProduct(Product product) throws InvalidInputException {
        if (product.getName() == null || product.getName().trim().isEmpty())
            throw new InvalidInputException("Product name cannot be empty.");
        if (product.getPrice() < 0)
            throw new InvalidInputException("Price cannot be negative.");
        if (product.getQuantity() < 0)
            throw new InvalidInputException("Quantity cannot be negative.");

        String sql = "INSERT INTO products (name, price, quantity, category, extra1, extra2) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setString(4, product.getCategory());

            if (product instanceof ElectronicsProduct e) {
                ps.setString(5, e.getBrand());
                ps.setString(6, String.valueOf(e.getWarrantyMonths()));
            } else if (product instanceof GroceryProduct g) {
                ps.setString(5, g.getExpiryDate());
                ps.setString(6, g.isOrganic() ? "true" : "false");
            } else if (product instanceof ClothingProduct c) {
                ps.setString(5, c.getSize());
                ps.setString(6, c.getColor());
            } else {
                ps.setString(5, "");
                ps.setString(6, "");
            }

            ps.executeUpdate();
            System.out.println("✔ Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // VIEW ALL PRODUCTS
    // ─────────────────────────────────────────────
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return products;
    }

    // ─────────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────────
    public Product getProductById(int id) throws ProductNotFoundException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }

    // ─────────────────────────────────────────────
    // UPDATE PRODUCT
    // ─────────────────────────────────────────────
    public void updateProduct(int id, String name, double price, int quantity)
            throws ProductNotFoundException, InvalidInputException {
        if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Name cannot be empty.");
        if (price < 0) throw new InvalidInputException("Price cannot be negative.");
        if (quantity < 0) throw new InvalidInputException("Quantity cannot be negative.");

        // Check exists
        getProductById(id);

        String sql = "UPDATE products SET name=?, price=?, quantity=? WHERE product_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.setInt(4, id);
            ps.executeUpdate();
            System.out.println("✔ Product updated successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // DELETE PRODUCT
    // ─────────────────────────────────────────────
    public void deleteProduct(int id) throws ProductNotFoundException {
        getProductById(id); // throws if not found
        String sql = "DELETE FROM products WHERE product_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✔ Product deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // SELL PRODUCT (reduces stock)
    // ─────────────────────────────────────────────
    public void sellProduct(int id, int qty)
            throws ProductNotFoundException, InsufficientStockException {
        Product p = getProductById(id);
        if (p.getQuantity() < qty)
            throw new InsufficientStockException("Not enough stock! Available: " + p.getQuantity());

        String sql = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("✔ Sale recorded. " + qty + " unit(s) sold.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // SEARCHABLE INTERFACE METHODS
    // ─────────────────────────────────────────────
    @Override
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) result.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Product> searchByCategory(String category) {
        List<Product> result = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) result.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return result;
    }

    // ─────────────────────────────────────────────
    // REPORTABLE INTERFACE METHODS
    // ─────────────────────────────────────────────
    @Override
    public void generateReport() {
        List<Product> all = getAllProducts();
        System.out.println("\n========== FULL INVENTORY REPORT ==========");
        if (all.isEmpty()) { System.out.println("No products found."); return; }
        double totalValue = 0;
        for (Product p : all) {
            System.out.println(p);
            totalValue += p.getFinalPrice() * p.getQuantity();
        }
        System.out.printf("Total Inventory Value: Rs. %.2f%n", totalValue);
        System.out.println("===========================================\n");
    }

    @Override
    public void generateLowStockReport(int threshold) {
        System.out.println("\n===== LOW STOCK REPORT (Qty < " + threshold + ") =====");
        List<Product> all = getAllProducts();
        boolean found = false;
        for (Product p : all) {
            if (p.getQuantity() < threshold) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No low-stock products.");
        System.out.println("============================================\n");
    }

    // ─────────────────────────────────────────────
    // HELPER: Map ResultSet row to Product object
    // ─────────────────────────────────────────────
    private Product mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("product_id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int qty = rs.getInt("quantity");
        String cat = rs.getString("category");
        String extra1 = rs.getString("extra1");
        String extra2 = rs.getString("extra2");

        return switch (cat) {
            case "Electronics" -> new ElectronicsProduct(id, name, price, qty,
                    extra1 != null ? extra1 : "",
                    extra2 != null ? Integer.parseInt(extra2) : 0);
            case "Grocery" -> new GroceryProduct(id, name, price, qty,
                    extra1 != null ? extra1 : "",
                    "true".equals(extra2));
            case "Clothing" -> new ClothingProduct(id, name, price, qty,
                    extra1 != null ? extra1 : "",
                    extra2 != null ? extra2 : "");
            default -> new ElectronicsProduct(id, name, price, qty, "", 0);
        };
    }
}
