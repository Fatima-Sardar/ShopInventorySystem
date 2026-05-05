package model;

// Subclass - Inheritance from Product
public class ClothingProduct extends Product {
    private String size;
    private String color;

    public ClothingProduct(int productId, String name, double price, int quantity, String size, String color) {
        super(productId, name, price, quantity, "Clothing");
        this.size = size;
        this.color = color;
    }

    @Override
    public double calculateDiscount() {
        // 15% discount on clothing
        return getPrice() * 0.15;
    }

    public String getSize() { return size; }
    public String getColor() { return color; }
    public void setSize(String size) { this.size = size; }
    public void setColor(String color) { this.color = color; }

    @Override
    public String toString() {
        return super.toString() + String.format("\n  Size     : %s\n  Color    : %s", size, color);
    }
}
