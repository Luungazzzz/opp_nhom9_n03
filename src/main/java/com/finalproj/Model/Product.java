package com.finalproj.Model;

import javafx.beans.property.*;

public class Product {
    private IntegerProperty productId;
    private StringProperty brand;
    private StringProperty model;
    private IntegerProperty ram;
    private DoubleProperty price;

    // Constructor không cần productId (do CSDL sẽ tự tạo ID)
    public Product(String brand, String model, int ram, double price) {
        this.productId = new SimpleIntegerProperty();  // ID sẽ được lấy từ CSDL khi thêm vào
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.ram = new SimpleIntegerProperty(ram);
        this.price = new SimpleDoubleProperty(price);
    }

    // Constructor đầy đủ khi lấy dữ liệu từ CSDL (bao gồm productId)
    public Product(int productId, String brand, String model, int ram, double price) {
        this.productId = new SimpleIntegerProperty(productId);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.ram = new SimpleIntegerProperty(ram);
        this.price = new SimpleDoubleProperty(price);
    }

    // Getters and Property methods
    public int getProductId() { return productId.get(); }
    public IntegerProperty productIdProperty() { return productId; }

    public String getBrand() { return brand.get(); }
    public StringProperty brandProperty() { return brand; }

    public String getModel() { return model.get(); }
    public StringProperty modelProperty() { return model; }

    public int getRam() { return ram.get(); }
    public IntegerProperty ramProperty() { return ram; }

    public double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }

    // Setters
    public void setBrand(String brand) { this.brand.set(brand); }
    public void setModel(String model) { this.model.set(model); }
    public void setRam(int ram) { this.ram.set(ram); }
    public void setPrice(double price) { this.price.set(price); }
}
