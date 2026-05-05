package model;

// Subclass - Inheritance from Product
public class GroceryProduct extends Product {
    private String expiryDate;
    private boolean isOrganic;

    public GroceryProduct(int productId, String name, double price, int quantity, String expiryDate, boolean isOrganic) {
        super(productId, name, price, quantity, "Grocery");
        this.expiryDate = expiryDate;
        this.isOrganic = isOrganic;
    }

    @Override
    public double calculateDiscount() {
        // 5% discount on organic products
        if (isOrganic) return getPrice() * 0.05;
        return 0;
    }

    public String getExpiryDate() { return expiryDate; }
    public boolean isOrganic() { return isOrganic; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public void setOrganic(boolean organic) { this.isOrganic = organic; }

    @Override
    public String toString() {
        return super.toString() + String.format("\n  Expiry   : %s\n  Organic  : %s", expiryDate, isOrganic ? "Yes" : "No");
    }
}
