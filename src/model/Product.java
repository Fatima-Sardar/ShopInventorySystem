package model;

// Abstract base class demonstrating abstraction and encapsulation
public abstract class Product {
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private String category;

    public Product(int productId, String name, double price, int quantity, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // Abstract method - each product type must implement
    public abstract double calculateDiscount();

    // Getters
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }

    // Setters with validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Product name cannot be empty.");
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantity = quantity;
    }

    public void setCategory(String category) { this.category = category; }

    public double getFinalPrice() {
        return price - calculateDiscount();
    }

    @Override
    public String toString() {
        return String.format(
            "------------------------------------------------------------\n" +
            "  ID       : %d\n" +
            "  Name     : %s\n" +
            "  Category : %s\n" +
            "  Price    : Rs. %.2f\n" +
            "  Qty      : %d",
            productId, name, category, getFinalPrice(), quantity);
    }
}
