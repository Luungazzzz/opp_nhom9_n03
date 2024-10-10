package com.finalproj.Model;

import javafx.beans.property.*;

public class Invoice {
    private IntegerProperty id;
    private IntegerProperty customerId;
    private StringProperty productId;
    private IntegerProperty quantity;
    private DoubleProperty totalPrice;

    // Constructor
    public Invoice(int id, int customerId, String productId, int quantity, double totalPrice) {
        this.id = new SimpleIntegerProperty(id);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.productId = new SimpleStringProperty(productId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    // Getters and Property methods
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public int getCustomerId() { return customerId.get(); }
    public IntegerProperty customerIdProperty() { return customerId; }

    public String getProductId() { return productId.get(); }
    public StringProperty productIdProperty() { return productId; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }

    public double getTotalPrice() { return totalPrice.get(); }
    public DoubleProperty totalPriceProperty() { return totalPrice; }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setCustomerId(int customerId) { this.customerId.set(customerId); }
    public void setProductId(String productId) { this.productId.set(productId); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public void setTotalPrice(double totalPrice) { this.totalPrice.set(totalPrice); }
}
