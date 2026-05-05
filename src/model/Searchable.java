package model;

import java.util.List;

// Interface for search functionality
public interface Searchable {
    List<Product> searchByName(String name);
    List<Product> searchByCategory(String category);
}
