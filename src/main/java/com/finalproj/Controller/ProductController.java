package com.finalproj.Controller;

import com.finalproj.Database.Database;
import com.finalproj.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductController {

    @FXML
    private TextField brandField, modelField, ramField, priceField, quantityField, searchField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> colProductId, colRAM, colQuantity;
    @FXML
    private TableColumn<Product, String> colBrand, colModel;
    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private Button addButton, editButton, deleteButton, backButton;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public void initialize() {
        colProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
        colBrand.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        colModel.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        colRAM.setCellValueFactory(cellData -> cellData.getValue().ramProperty().asObject());

        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        colPrice.setCellFactory(column -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.0f", price));  // Hiển thị định dạng số mà không có chữ "đồng"
                }
            }
        });

        colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        loadProducts();

        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    // Tải danh sách sản phẩm từ cơ sở dữ liệu
    private void loadProducts() {
        productList.clear();
        String query = "SELECT * FROM products";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int ram = rs.getInt("ram");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                Product product = new Product(productId, brand, model, ram, price, quantity);
                productList.add(product);
            }
            productTable.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hiển thị chi tiết sản phẩm đã chọn
    private void showProductDetails(Product product) {
        if (product != null) {
            brandField.setText(product.getBrand());
            modelField.setText(product.getModel());
            ramField.setText(String.valueOf(product.getRam()));

            // Định dạng giá để hiển thị trên TextField mà không có ký tự 'E'
            priceField.setText(String.format("%,.0f", product.getPrice()));

            quantityField.setText(String.valueOf(product.getQuantity()));
        }
    }
    // Tìm kiếm sản phẩm theo mã sản phẩm
    @FXML
    private void handleSearchProduct() {
        String searchId = searchField.getText();
        if (searchId == null || searchId.isEmpty()) {
            productTable.setItems(productList);
            return;
        }

        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        try {
            int searchProductId = Integer.parseInt(searchId);
            for (Product product : productList) {
                if (product.getProductId() == searchProductId) {
                    filteredProducts.add(product);
                }
            }
            if (filteredProducts.isEmpty()) {
                showAlert("Thông báo", "Không tìm thấy sản phẩm với Mã Sản Phẩm này.");
            }
            productTable.setItems(filteredProducts);
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Mã Sản Phẩm phải là số hợp lệ.");
        }
    }

    // Thêm sản phẩm vào cơ sở dữ liệu
    @FXML
    private void handleAddProduct() {
        try {
            String brand = brandField.getText();
            String model = modelField.getText();
            int ram = Integer.parseInt(ramField.getText());
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            String query = "INSERT INTO products (brand, model, ram, price, quantity) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, brand);
                stmt.setString(2, model);
                stmt.setInt(3, ram);
                stmt.setDouble(4, price);
                stmt.setInt(5, quantity);
                stmt.executeUpdate();

                showAlert("Thành công", "Sản phẩm đã được thêm thành công.");
                loadProducts();
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Đã xảy ra lỗi khi thêm sản phẩm.");
        }
    }

    // Sửa thông tin sản phẩm
    // Sửa thông tin sản phẩm
    @FXML
    private void handleEditProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                int ram = Integer.parseInt(ramField.getText());

                // Loại bỏ dấu phẩy trước khi chuyển đổi thành double
                String priceText = priceField.getText().replace(",", "");
                double price = Double.parseDouble(priceText);

                int quantity = Integer.parseInt(quantityField.getText());

                String query = "UPDATE products SET brand = ?, model = ?, ram = ?, price = ?, quantity = ? WHERE product_id = ?";
                try (Connection conn = Database.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, brand);
                    stmt.setString(2, model);
                    stmt.setInt(3, ram);
                    stmt.setDouble(4, price);
                    stmt.setInt(5, quantity);
                    stmt.setInt(6, selectedProduct.getProductId());
                    stmt.executeUpdate();

                    showAlert("Thành công", "Thông tin sản phẩm đã được cập nhật.");
                    loadProducts();
                    clearFields();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Đã xảy ra lỗi khi sửa sản phẩm.");
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một sản phẩm để sửa.");
        }
    }


    // Xóa sản phẩm
    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String query = "DELETE FROM products WHERE product_id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, selectedProduct.getProductId());
                stmt.executeUpdate();

                showAlert("Thành công", "Sản phẩm đã được xóa.");
                loadProducts();
                clearFields();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Đã xảy ra lỗi khi xóa sản phẩm.");
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một sản phẩm để xóa.");
        }
    }

    // Giảm số lượng sản phẩm khi xuất hóa đơn
    public void reduceProductQuantity(int productId, int quantitySold) {
        String query = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantitySold);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể cập nhật số lượng sản phẩm.");
        }
    }

    // Xóa thông tin các trường nhập liệu
    private void clearFields() {
        brandField.clear();
        modelField.clear();
        ramField.clear();
        priceField.clear();
        quantityField.clear();
    }

    // Quay lại màn hình chính
    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể quay lại màn hình chính.");
        }
    }

    // Hiển thị thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
