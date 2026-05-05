-- ============================================
-- Shop Inventory Management System - Database
-- ============================================

CREATE DATABASE IF NOT EXISTS shop_inventory;
USE shop_inventory;

CREATE TABLE IF NOT EXISTS products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100)   NOT NULL,
    price      DECIMAL(10,2)  NOT NULL CHECK (price >= 0),
    quantity   INT            NOT NULL CHECK (quantity >= 0),
    category   VARCHAR(50)    NOT NULL,
    extra1     VARCHAR(100),   -- brand / expiryDate / size
    extra2     VARCHAR(100)    -- warrantyMonths / isOrganic / color
);

-- Sample data
INSERT INTO products (name, price, quantity, category, extra1, extra2) VALUES
('Samsung Galaxy A15',  35000, 20, 'Electronics', 'Samsung', '12'),
('USB-C Fast Charger',   2500,  50, 'Electronics', 'Anker',   '6'),
('Basmati Rice 5kg',     750,  100, 'Grocery',    '12-12-2025', 'false'),
('Organic Honey 500g',  1200,   30, 'Grocery',    '06-06-2026', 'true'),
('Men Kurta Shalwar',   2800,   40, 'Clothing',   'L',  'White'),
('Ladies Lawn Suit',    3500,   25, 'Clothing',   'M',  'Pink');
