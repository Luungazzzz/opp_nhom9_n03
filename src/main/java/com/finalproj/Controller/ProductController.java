package com.finalproj.Controller;

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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductController {

    @FXML
    private TextField brandField, modelField, ramField, priceField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> colProductId;
    @FXML
    private TableColumn<Product, String> colBrand, colModel;
    @FXML
    private TableColumn<Product, Integer> colRAM;
    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private Button addProductBtn, editProductBtn, deleteProductBtn, backButton;

    private ObservableList<Product> productList;

    // Định dạng tiền tệ cho Việt Nam
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public void initialize() {
        productList = FXCollections.observableArrayList();
        colProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
        colBrand.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        colModel.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        colRAM.setCellValueFactory(cellData -> cellData.getValue().ramProperty().asObject());

        // Định dạng giá để hiển thị
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        colPrice.setCellFactory(column -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.0f VND", price));  // Hiển thị giá trị định dạng tiền tệ
                }
            }
        });

        loadProducts();

        // Lắng nghe sự kiện chọn sản phẩm trong bảng và hiển thị chi tiết sản phẩm
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    // Phương thức kiểm tra xem sản phẩm đã tồn tại hay chưa
    private boolean isProductExist(String brand, String model, int ram, double price) {
        String query = "SELECT COUNT(*) FROM products WHERE brand = ? AND model = ? AND ram = ? AND price = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setInt(3, ram);
            stmt.setDouble(4, price);
            ResultSet rs = stmt.executeQuery();

            // Nếu có ít nhất 1 sản phẩm trùng, trả về true
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  // Không tìm thấy sản phẩm
    }

    // Tải danh sách sản phẩm từ database
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

                Product product = new Product(productId, brand, model, ram, price);
                productList.add(product);
            }
            productTable.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hiển thị thông tin sản phẩm đã chọn lên các trường nhập liệu
    private void showProductDetails(Product selectedProduct) {
        if (selectedProduct != null) {
            brandField.setText(selectedProduct.getBrand());
            modelField.setText(selectedProduct.getModel());
            ramField.setText(String.valueOf(selectedProduct.getRam()));
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
        }
    }

    // Xử lý thêm sản phẩm
    @FXML
    private void handleAddProduct() {
        try {
            String brand = brandField.getText();
            String model = modelField.getText();
            int ram = Integer.parseInt(ramField.getText());
            double price = Double.parseDouble(priceField.getText());

            // Kiểm tra dữ liệu đầu vào
            if (!isValidInput()) {
                return;
            }

            if (isProductExist(brand, model, ram, price)) {
                showAlert("Lỗi", "Sản phẩm đã tồn tại. Vui lòng thêm sản phẩm khác.");
                return;
            }

            // Thêm sản phẩm vào cơ sở dữ liệu nếu sản phẩm chưa tồn tại
            String query = "INSERT INTO products (brand, model, ram, price) VALUES (?, ?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, brand);
                stmt.setString(2, model);
                stmt.setInt(3, ram);
                stmt.setDouble(4, price);
                stmt.executeUpdate();
                clearFields();
                loadProducts();  // Tải lại danh sách sản phẩm
                showAlert("Thành công", "Sản phẩm đã được thêm thành công!");
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "RAM và giá phải là số hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Đã xảy ra lỗi khi thêm sản phẩm.");
        }
    }

    // Xử lý lưu thông tin sản phẩm sau khi chỉnh sửa
    @FXML
    private void handleEditProduct() {
        try {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                String brand = brandField.getText();
                String model = modelField.getText();
                int ram = Integer.parseInt(ramField.getText());
                double price = Double.parseDouble(priceField.getText());

                if (!isValidInput()) {
                    return;
                }

                String query = "UPDATE products SET brand = ?, model = ?, ram = ?, price = ? WHERE product_id = ?";
                try (Connection conn = Database.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, brand);
                    stmt.setString(2, model);
                    stmt.setInt(3, ram);
                    stmt.setDouble(4, price);
                    stmt.setInt(5, selectedProduct.getProductId());
                    stmt.executeUpdate();

                    showAlert("Thành công", "Cập nhật thông tin sản phẩm thành công!");
                    clearFields();
                    loadProducts();  // Tải lại danh sách sản phẩm
                }
            } else {
                showAlert("Lỗi", "Vui lòng chọn một sản phẩm để sửa.");
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "RAM và giá phải là số hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Đã xảy ra lỗi khi chỉnh sửa sản phẩm.");
        }
    }

    // Xử lý xóa sản phẩm
    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String query = "DELETE FROM products WHERE product_id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, selectedProduct.getProductId());
                stmt.executeUpdate();

                showAlert("Thành công", "Xóa sản phẩm thành công!");
                clearFields();  // Xóa các trường nhập liệu
                loadProducts();  // Tải lại danh sách sản phẩm
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Đã xảy ra lỗi khi xóa sản phẩm.");
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một sản phẩm để xóa.");
        }
    }

    // Xử lý quay lại màn hình chính
    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        switchScene(event, "/com/Dashboard.fxml");
    }

    // Phương thức chuyển cảnh giao diện
    private void switchScene(ActionEvent event, String fxmlFilePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFilePath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể chuyển cảnh: " + e.getMessage());
        }
    }

    // Kiểm tra dữ liệu đầu vào
    private boolean isValidInput() {
        String brand = brandField.getText();
        String model = modelField.getText();
        if (brand.isEmpty() || model.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin sản phẩm.");
            return false;
        }
        try {
            Integer.parseInt(ramField.getText());
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "RAM và giá phải là số hợp lệ.");
            return false;
        }
        return true;
    }

    // Hiển thị thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Xóa dữ liệu trong các trường nhập liệu
    private void clearFields() {
        brandField.clear();
        modelField.clear();
        ramField.clear();
        priceField.clear();
    }
}

