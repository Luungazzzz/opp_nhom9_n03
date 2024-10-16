package com.finalproj.Controller;

import com.finalproj.Database.Database;
import com.finalproj.Model.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class InvoiceController {

    @FXML
    private ComboBox<String> customerComboBox, productComboBox;
    @FXML
    private TextField quantityField, totalPriceField;

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> colInvoiceId, colInvoiceCustomerId, colInvoiceQuantity;
    @FXML
    private TableColumn<Invoice, String> colInvoiceProductId;
    @FXML
    private TableColumn<Invoice, Double> colInvoiceTotalPrice;
    @FXML
    private Button addInvoiceBtn, editInvoiceBtn, deleteInvoiceBtn, exportInvoiceBtn, backButton;

    private ObservableList<Invoice> invoiceList;

    private double productPrice;
    private static final DecimalFormat currencyFormat = new DecimalFormat("#,###.00 VND");

    public void initialize() {
        invoiceList = FXCollections.observableArrayList();

        // Liên kết cột với thuộc tính
        colInvoiceId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colInvoiceCustomerId.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        colInvoiceProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        colInvoiceQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        colInvoiceTotalPrice.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        // Định dạng lại cột Tổng tiền
        colInvoiceTotalPrice.setCellFactory(column -> {
            return new TableCell<Invoice, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // Định dạng số tiền thành VND
                        setText(currencyFormat.format(item));
                    }
                }
            };
        });

        loadCustomers(); // Tải danh sách khách hàng vào ComboBox
        loadProducts();  // Tải danh sách sản phẩm vào ComboBox
        loadInvoices();  // Tải danh sách hóa đơn từ database

        productComboBox.valueProperty().addListener((observable, oldValue, newValue) -> loadProductPrice(newValue));
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
        invoiceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showInvoiceDetails(newValue));
    }

    // Tải danh sách khách hàng từ cơ sở dữ liệu
    private void loadCustomers() {
        String query = "SELECT id FROM customers";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customerComboBox.getItems().add(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải danh sách khách hàng: " + e.getMessage());
        }
    }

    // Tải danh sách sản phẩm từ cơ sở dữ liệu
    private void loadProducts() {
        String query = "SELECT product_id FROM products";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productComboBox.getItems().add(rs.getString("product_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải danh sách sản phẩm: " + e.getMessage());
        }
    }

    // Hiển thị chi tiết hóa đơn đã chọn
    private void showInvoiceDetails(Invoice selectedInvoice) {
        if (selectedInvoice != null) {
            customerComboBox.setValue(String.valueOf(selectedInvoice.getCustomerId()));
            productComboBox.setValue(selectedInvoice.getProductId());
            quantityField.setText(String.valueOf(selectedInvoice.getQuantity()));
            totalPriceField.setText(currencyFormat.format(selectedInvoice.getTotalPrice()));
        }
    }

    // Lấy giá của sản phẩm dựa trên mã sản phẩm được chọn
    private void loadProductPrice(String productId) {
        String query = "SELECT price FROM products WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                productPrice = rs.getDouble("price");  // Lưu giá của sản phẩm
                calculateTotalPrice();  // Tính toán lại tổng tiền
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải giá sản phẩm: " + e.getMessage());
        }
    }

    // Tính tổng tiền dựa trên giá sản phẩm và số lượng
    private void calculateTotalPrice() {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            double totalPrice = quantity * productPrice;  // Tính tổng tiền
            totalPriceField.setText(currencyFormat.format(totalPrice));  // Hiển thị tổng tiền
        } catch (NumberFormatException e) {
            totalPriceField.setText("");  // Nếu số lượng không hợp lệ, không tính tổng tiền
        }
    }
    @FXML
    private TextField searchField;

    @FXML
    private void handleSearchInvoice() {
        String searchTerm = searchField.getText();  // Lấy giá trị tìm kiếm từ searchField

        // Kiểm tra nếu searchTerm trống thì hiển thị tất cả các hóa đơn
        if (searchTerm == null || searchTerm.isEmpty()) {
            invoiceTable.setItems(invoiceList);  // Đưa lại danh sách đầy đủ
            return;
        }

        // Tạo danh sách hóa đơn lọc theo điều kiện tìm kiếm
        ObservableList<Invoice> filteredInvoices = FXCollections.observableArrayList();
        for (Invoice invoice : invoiceList) {
            if (invoice.getId() == Integer.parseInt(searchTerm)) {
                filteredInvoices.add(invoice);
            }
        }

        // Nếu không tìm thấy hóa đơn
        if (filteredInvoices.isEmpty()) {
            showAlert("Thông báo", "Không tìm thấy hóa đơn với mã này.");
        }

        // Cập nhật bảng với danh sách lọc
        invoiceTable.setItems(filteredInvoices);
    }

    @FXML
    private void handleAddInvoice() {
        try {
            String customerId = customerComboBox.getValue();  // Lấy mã khách hàng từ ComboBox
            String productId = productComboBox.getValue();    // Lấy mã sản phẩm từ ComboBox
            int quantity = Integer.parseInt(quantityField.getText());
            double totalPrice = Double.parseDouble(totalPriceField.getText().replace(" VND", "").replace(",", ""));

            // Kiểm tra số lượng sản phẩm trong kho
            if (!isProductAvailable(productId, quantity)) {
                showAlert("Lỗi", "Số lượng sản phẩm trong kho không đủ để xuất hóa đơn.");
                return;  // Dừng lại nếu số lượng không đủ
            }

            // Truy vấn thêm hóa đơn vào bảng invoices
            String query = "INSERT INTO invoices (customer_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, customerId);
                stmt.setString(2, productId);
                stmt.setInt(3, quantity);
                stmt.setDouble(4, totalPrice);
                stmt.executeUpdate();

                // Cập nhật số lượng sản phẩm sau khi tạo hóa đơn
                updateProductQuantity(productId, quantity);

                // Cập nhật lại bảng hiển thị
                loadInvoices();
                showAlert("Thành công", "Hóa đơn đã được thêm thành công!");

                clearFields();
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Các trường số lượng và tổng tiền phải là số hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Đã xảy ra lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    // Kiểm tra số lượng sản phẩm có đủ trong kho
    private boolean isProductAvailable(String productId, int quantityRequested) {
        String query = "SELECT quantity FROM products WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int availableQuantity = rs.getInt("quantity");
                return availableQuantity >= quantityRequested;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể kiểm tra số lượng sản phẩm.");
        }
        return false;
    }

    // Hàm cập nhật số lượng sản phẩm sau khi tạo hóa đơn
    // Hàm cập nhật số lượng sản phẩm sau khi tạo hóa đơn
    private void updateProductQuantity(String productId, int quantitySold) {
        String query = "SELECT quantity FROM products WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Kiểm tra số lượng sản phẩm hiện tại trong kho
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int availableQuantity = rs.getInt("quantity");

                // Nếu số lượng còn lại sau khi bán nhỏ hơn 0, không cho phép thực hiện
                if (availableQuantity - quantitySold < 0) {
                    showAlert("Lỗi", "Số lượng sản phẩm trong kho không đủ.");
                    return;  // Ngừng việc giảm số lượng sản phẩm
                }

                // Nếu đủ, cập nhật số lượng sản phẩm
                String updateQuery = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, quantitySold);
                    updateStmt.setString(2, productId);
                    updateStmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể cập nhật số lượng sản phẩm.");
        }
    }



    // Xử lý sửa hóa đơn
    @FXML
    private void handleEditInvoice() {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice != null) {
            try {
                String customerId = customerComboBox.getValue();
                String productId = productComboBox.getValue();
                int quantity = Integer.parseInt(quantityField.getText());
                double totalPrice = Double.parseDouble(totalPriceField.getText().replace(" VND", "").replace(",", ""));

                String query = "UPDATE invoices SET customer_id = ?, product_id = ?, quantity = ?, total_price = ? WHERE id = ?";
                try (Connection conn = Database.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, customerId);
                    stmt.setString(2, productId);
                    stmt.setInt(3, quantity);
                    stmt.setDouble(4, totalPrice);
                    stmt.setInt(5, selectedInvoice.getId());
                    stmt.executeUpdate();

                    showAlert("Thành công", "Thông tin hóa đơn đã được cập nhật thành công!");
                    loadInvoices();  // Tải lại danh sách hóa đơn

                    clearFields();
                }
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Số lượng và tổng tiền phải là số hợp lệ.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Đã xảy ra lỗi khi cập nhật hóa đơn: " + e.getMessage());
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một hóa đơn để sửa.");
        }
    }

    // Xử lý xóa hóa đơn
    @FXML
    private void handleDeleteInvoice() {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice != null) {
            String query = "DELETE FROM invoices WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, selectedInvoice.getId());
                stmt.executeUpdate();
                invoiceList.remove(selectedInvoice);  // Xóa khỏi danh sách hiển thị
                showAlert("Thành công", "Hóa đơn đã được xóa thành công!");
                loadInvoices();  // Tải lại danh sách hóa đơn

                clearFields();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Đã xảy ra lỗi khi xóa hóa đơn: " + e.getMessage());
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một hóa đơn để xóa.");
        }
    }

    @FXML
    private void handleExportInvoice() {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice != null) {
            try {
                // Định dạng thời gian hiện tại
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                // Lấy thông tin khách hàng và sản phẩm từ CSDL
                String customerQuery = "SELECT name, address FROM customers WHERE id = ?";
                String productQuery = "SELECT model FROM products WHERE product_id = ?";

                String customerName = "";
                String customerAddress = "";
                String productName = "";

                try (Connection conn = Database.getConnection()) {
                    // Lấy thông tin khách hàng
                    try (PreparedStatement stmt = conn.prepareStatement(customerQuery)) {
                        stmt.setInt(1, selectedInvoice.getCustomerId());
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            customerName = rs.getString("name");
                            customerAddress = rs.getString("address");
                        }
                    }

                    // Lấy thông tin sản phẩm
                    try (PreparedStatement stmt = conn.prepareStatement(productQuery)) {
                        stmt.setString(1, selectedInvoice.getProductId());
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            productName = rs.getString("model");
                        }
                    }
                }

                String filePath = "src/main/resources/Files/Invoice.txt";  // Đường dẫn file xuất

                // Ghi thông tin hóa đơn vào file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Mở file ở chế độ append
                    writer.write("HÓA ĐƠN #" + selectedInvoice.getId() + "\n");
                    writer.write("Thời gian: " + dtf.format(now) + "\n");  // Ghi thời gian hiện tại
                    writer.write("Tên khách hàng: " + customerName + "\n");
                    writer.write("Địa chỉ: " + customerAddress + "\n");
                    writer.write("Sản phẩm: " + productName + "\n");  // Ghi model sản phẩm
                    writer.write("Số lượng: " + selectedInvoice.getQuantity() + "\n");
                    writer.write("Tổng tiền: " + currencyFormat.format(selectedInvoice.getTotalPrice()) + "\n");
                    writer.write("-----------------------------\n");
                    showAlert("Thành công", "Hóa đơn đã được xuất thành công vào file " + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Lỗi", "Không thể xuất hóa đơn: " + e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Không thể xuất hóa đơn: " + e.getMessage());
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một hóa đơn để xuất.");
        }
    }
    // Tải danh sách hóa đơn từ database
    private void loadInvoices() {
        invoiceList.clear();
        String query = "SELECT * FROM invoices";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                invoiceList.add(new Invoice(rs.getInt("id"), rs.getInt("customer_id"),
                        rs.getString("product_id"), rs.getInt("quantity"), rs.getDouble("total_price")));
            }
            invoiceTable.setItems(invoiceList);  // Đổ dữ liệu vào TableView
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải danh sách hóa đơn: " + e.getMessage());
        }
    }

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

    // Hiển thị thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        customerComboBox.setValue(null);
        productComboBox.setValue(null);
        quantityField.clear();
        totalPriceField.clear();

    }
}