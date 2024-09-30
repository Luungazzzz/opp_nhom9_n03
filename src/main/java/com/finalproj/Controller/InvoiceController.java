package com.finalproj.Controller;

import com.finalproj.Model.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableColumn<Invoice, Integer> colInvoiceId, colInvoiceCustomerId, colInvoiceProductId, colInvoiceQuantity;

    @FXML
    private TableColumn<Invoice, Double> colInvoiceTotalPrice;

    @FXML
    private Button addInvoiceBtn, deleteInvoiceBtn, exportInvoiceBtn;

    private ObservableList<Invoice> invoiceList;

    public void initialize() {
        invoiceList = FXCollections.observableArrayList();
        loadInvoices();
    }

    // Tải danh sách hóa đơn từ database
    private void loadInvoices() {
        String query = "SELECT * FROM invoices";
        try (Connection conn = Database.connect();
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

    // Xử lý thêm hóa đơn
    @FXML
    private void handleAddInvoice() {
        int customerId = Integer.parseInt(customerIdField.getText());
        String productId = productIdField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double totalPrice = Double.parseDouble(totalPriceField.getText());

        String query = "INSERT INTO invoices (customer_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, totalPrice);
            stmt.executeUpdate();
            invoiceList.add(new Invoice(0, customerId, productId, quantity, totalPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xử lý xóa hóa đơn
    @FXML
    private void handleDeleteInvoice() {
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice != null) {
            String query = "DELETE FROM invoices WHERE id = ?";
            try (Connection conn = Database.connect();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, selectedInvoice.getId());
                stmt.executeUpdate();
                invoiceList.remove(selectedInvoice);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                System.out.println("Xuất hóa đơn thành công! Hóa đơn đã được lưu vào file " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

