package model;

// Interface for report generation
public interface Reportable {
    void generateReport();
    void generateLowStockReport(int threshold);
}
