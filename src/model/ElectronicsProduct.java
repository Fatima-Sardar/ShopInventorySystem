package model;

// Subclass - Inheritance from Product
public class ElectronicsProduct extends Product {
    private String brand;
    private int warrantyMonths;

    public ElectronicsProduct(int productId, String name, double price, int quantity, String brand, int warrantyMonths) {
        super(productId, name, price, quantity, "Electronics");
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public double calculateDiscount() {
        // 10% discount on electronics above Rs. 5000
        if (getPrice() > 5000) return getPrice() * 0.10;
        return 0;
    }

    public String getBrand() { return brand; }
    public int getWarrantyMonths() { return warrantyMonths; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setWarrantyMonths(int months) { this.warrantyMonths = months; }

    @Override
    public String toString() {
        return super.toString() + String.format("\n  Brand    : %s\n  Warranty : %d months", brand, warrantyMonths);
    }
}
