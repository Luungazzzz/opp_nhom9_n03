package com.finalproj.Controller;

import com.finalproj.Model.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

// Các thư viện cần thiết cho việc xuất file
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvoiceController {

    @FXML
    private TextField customerIdField, productIdField, quantityField, totalPriceField;

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> colInvoiceId, colInvoiceCustomerId, colInvoiceQuantity;
    @FXML
    private TableColumn<Invoice, String> colInvoiceProductId;
    @FXML
    private TableColumn<Invoice, Double> colInvoiceTotalPrice;

    @FXML
    private Button addInvoiceBtn, editInvoiceBtn, deleteInvoiceBtn, exportInvoiceBtn;

    private ObservableList<Invoice> invoiceList;

    public void initialize() {
        invoiceList = FXCollections.observableArrayList();

        // Liên kết cột với thuộc tính
        colInvoiceId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colInvoiceCustomerId.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        colInvoiceProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        colInvoiceQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        colInvoiceTotalPrice.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        loadInvoices();

        // Hiển thị thông tin chi tiết hóa đơn trong các trường khi chọn một hàng
        invoiceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showInvoiceDetails(newValue));
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
            invoiceTable.setItems(invoiceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hiển thị thông tin hóa đơn đã chọn trong các trường nhập liệu
    private void showInvoiceDetails(Invoice selectedInvoice) {
        if (selectedInvoice != null) {
            customerIdField.setText(String.valueOf(selectedInvoice.getCustomerId()));
            productIdField.setText(selectedInvoice.getProductId());
            quantityField.setText(String.valueOf(selectedInvoice.getQuantity()));
            totalPriceField.setText(String.valueOf(selectedInvoice.getTotalPrice()));
        }
    }

    // Xử lý thêm hóa đơn
    @FXML
    private void handleAddInvoice() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            String productId = productIdField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double totalPrice = Double.parseDouble(totalPriceField.getText());

            String query = "INSERT INTO invoices (customer_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, customerId);
                stmt.setString(2, productId);
                stmt.setInt(3, quantity);
                stmt.setDouble(4, totalPrice);
                stmt.executeUpdate();

                // Thêm vào danh sách hiển thị
                invoiceList.add(new Invoice(0, customerId, productId, quantity, totalPrice));
                clearFields();
                loadInvoices();  // Tải lại danh sách hóa đơn
                showAlert("Thành công", "Hóa đơn đã được thêm thành công!");
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Các trường số lượng và tổng tiền phải là số hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Đã xảy ra lỗi khi thêm hóa đơn.");
        }
    }

    // Xử lý chỉnh sửa thông tin hóa đơn
    @FXML
    private void handleEditInvoice() {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice != null) {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                String productId = productIdField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double totalPrice = Double.parseDouble(totalPriceField.getText());

                String query = "UPDATE invoices SET customer_id = ?, product_id = ?, quantity = ?, total_price = ? WHERE id = ?";
                try (Connection conn = Database.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, customerId);
                    stmt.setString(2, productId);
                    stmt.setInt(3, quantity);
                    stmt.setDouble(4, totalPrice);
                    stmt.setInt(5, selectedInvoice.getId());
                    stmt.executeUpdate();

                    showAlert("Thành công", "Thông tin hóa đơn đã được cập nhật thành công!");
                    clearFields();
                    loadInvoices();  // Tải lại danh sách hóa đơn
                }
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Các trường số lượng và tổng tiền phải là số hợp lệ.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Đã xảy ra lỗi khi cập nhật hóa đơn.");
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
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Lỗi", "Đã xảy ra lỗi khi xóa hóa đơn.");
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một hóa đơn để xóa.");
        }
    }

    // Xử lý xuất hóa đơn ra file
    @FXML
    private void handleExportInvoice() {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice != null) {
            String fileName = "Invoice_" + selectedInvoice.getId() + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("HÓA ĐƠN #" + selectedInvoice.getId() + "\n");
                writer.write("Mã khách hàng: " + selectedInvoice.getCustomerId() + "\n");
                writer.write("Mã sản phẩm: " + selectedInvoice.getProductId() + "\n");
                writer.write("Số lượng: " + selectedInvoice.getQuantity() + "\n");
                writer.write("Tổng tiền: " + selectedInvoice.getTotalPrice() + " VND\n");
                showAlert("Thành công", "Hóa đơn đã được xuất thành công vào file " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Lỗi", "Không thể xuất hóa đơn: " + e.getMessage());
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một hóa đơn để xuất.");
        }
    }

    // Xóa dữ liệu trong các trường nhập liệu
    private void clearFields() {
        customerIdField.clear();
        productIdField.clear();
        quantityField.clear();
        totalPriceField.clear();
    }

    // Hiển thị thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
