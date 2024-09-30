package com.finalproj.Model;

import java.util.UUID;

public class Product {
    private UUID productId;
    private String brand;
    private String model;
    private String operatingSystem;
    private String screenSize;
    private String cpu;
    private int ram;
    private int storageCapacity;
    private String rearCamera;
    private String frontCamera;
    private String battery;
    private String chargingTechnology;
    private String connectivity;
    private String designAndMaterial;
    private double price;

    // Constructor
    public Product(UUID productId, String brand, String model, String operatingSystem, String screenSize,
                   String cpu, int ram, int storageCapacity, String rearCamera, String frontCamera,
                   String battery, String chargingTechnology, String connectivity, String designAndMaterial,
                   double price) {
        this.productId = productId;
        this.brand = brand;
        this.model = model;
        this.operatingSystem = operatingSystem;
        this.screenSize = screenSize;
        this.cpu = cpu;
        this.ram = ram;
        this.storageCapacity = storageCapacity;
        this.rearCamera = rearCamera;
        this.frontCamera = frontCamera;
        this.battery = battery;
        this.chargingTechnology = chargingTechnology;
        this.connectivity = connectivity;
        this.designAndMaterial = designAndMaterial;
        this.price = price;
    }

    // Getters
    public UUID getProductId() {
        return productId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public String getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public String getRearCamera() {
        return rearCamera;
    }

    public String getFrontCamera() {
        return frontCamera;
    }

    public String getBattery() {
        return battery;
    }

    public String getChargingTechnology() {
        return chargingTechnology;
    }

    public String getConnectivity() {
        return connectivity;
    }

    public String getDesignAndMaterial() {
        return designAndMaterial;
    }

    public double getPrice() {
        return price;
    }
}
