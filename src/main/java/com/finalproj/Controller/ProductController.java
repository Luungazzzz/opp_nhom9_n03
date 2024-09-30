package com.finalproj.Controller;

import com.finalproj.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class ProductController {

    @FXML
    private TextField productIdField, brandField, modelField, osField, screenSizeField, cpuField, ramField,
            storageCapacityField, rearCameraField, frontCameraField, batteryField, chargingField,
            connectivityField, designField, priceField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> colProductId, colProductBrand, colProductModel, colProductOS,
            colProductScreen, colProductCPU, colProductRAM, colProductStorage, colProductRearCam, colProductFrontCam,
            colProductBattery, colProductCharging, colProductConnectivity, colProductDesign;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    @FXML
    private Button addProductBtn, deleteProductBtn;

    private ObservableList<Product> productList;

    public void initialize() {
        productList = FXCollections.observableArrayList();
        loadProducts();
    }

    // Tải danh sách sản phẩm từ database
    private void loadProducts() {
        String query = "SELECT * FROM products";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Chuyển đổi `product_id` thành `UUID`
                UUID productId = UUID.fromString(rs.getString("product_id"));

                productList.add(new Product(productId, rs.getString("brand"), rs.getString("model"),
                        rs.getString("operating_system"), rs.getString("screen_size"), rs.getString("cpu"),
                        rs.getInt("ram"), rs.getInt("storage_capacity"), rs.getString("rear_camera"),
                        rs.getString("front_camera"), rs.getString("battery"), rs.getString("charging_technology"),
                        rs.getString("connectivity"), rs.getString("design_and_material"), rs.getDouble("price")));
            }
            productTable.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xử lý thêm sản phẩm
    @FXML
    private void handleAddProduct() {
        UUID productId = UUID.randomUUID(); // Tạo UUID mới cho sản phẩm
        String brand = brandField.getText();
        String model = modelField.getText();
        String os = osField.getText();
        String screenSize = screenSizeField.getText();
        String cpu = cpuField.getText();
        int ram = Integer.parseInt(ramField.getText());
        int storage = Integer.parseInt(storageCapacityField.getText());
        String rearCamera = rearCameraField.getText();
        String frontCamera = frontCameraField.getText();
        String battery = batteryField.getText();
        String chargingTech = chargingField.getText();
        String connectivity = connectivityField.getText();
        String design = designField.getText();
        double price = Double.parseDouble(priceField.getText());

        String query = "INSERT INTO products (product_id, brand, model, operating_system, screen_size, cpu, ram, storage_capacity, rear_camera, front_camera, battery, charging_technology, connectivity, design_and_material, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productId.toString());
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setString(4, os);
            stmt.setString(5, screenSize);
            stmt.setString(6, cpu);
            stmt.setInt(7, ram);
            stmt.setInt(8, storage);
            stmt.setString(9, rearCamera);
            stmt.setString(10, frontCamera);
            stmt.setString(11, battery);
            stmt.setString(12, chargingTech);
            stmt.setString(13, connectivity);
            stmt.setString(14, design);
            stmt.setDouble(15, price);
            stmt.executeUpdate();

            productList.add(new Product(productId, brand, model, os, screenSize, cpu, ram, storage, rearCamera, frontCamera, battery, chargingTech, connectivity, design, price));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xử lý xóa sản phẩm
    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String query = "DELETE FROM products WHERE product_id = ?";
            try (Connection conn = Database.connect();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, selectedProduct.getProductId().toString());
                stmt.executeUpdate();

                productList.remove(selectedProduct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
