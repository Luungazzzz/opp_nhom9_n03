package com.finalproj.Model;

import javafx.beans.property.*;

public class Product {
    private final IntegerProperty productId;
    private final StringProperty brand;
    private final StringProperty model;
    private final IntegerProperty ram;
    private final DoubleProperty price;
    private final IntegerProperty quantity;

    public Product(int productId, String brand, String model, int ram, double price, int quantity) {
        this.productId = new SimpleIntegerProperty(productId);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.ram = new SimpleIntegerProperty(ram);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public int getProductId() {
        return productId.get();
    }

    public IntegerProperty productIdProperty() {
        return productId;
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getRam() {
        return ram.get();
    }

    public IntegerProperty ramProperty() {
        return ram;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
