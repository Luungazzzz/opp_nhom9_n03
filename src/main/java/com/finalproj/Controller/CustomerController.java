package com.finalproj.Controller;

import com.finalproj.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerController {

    @FXML
    private TextField nameField, addressField, phoneNumberField, emailField;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> colCustomerId;

    @FXML
    private TableColumn<Customer, String> colCustomerName, colCustomerAddress, colCustomerPhone, colCustomerEmail;

    @FXML
    private Button addCustomerBtn, deleteCustomerBtn;

    private ObservableList<Customer> customerList;

    public void initialize() {
        customerList = FXCollections.observableArrayList();
        loadCustomers();
    }

    // Tải danh sách khách hàng từ database
    private void loadCustomers() {
        String query = "SELECT * FROM customers";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customerList.add(new Customer(rs.getInt("id"), rs.getString("name"),
                        rs.getString("address"), rs.getString("phone_number"), rs.getString("email")));
            }
            customerTable.setItems(customerList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xử lý thêm khách hàng
    @FXML
    private void handleAddCustomer() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneNumberField.getText();
        String email = emailField.getText();

        // Kiểm tra email có đuôi @gmail.com
        if (!isValidEmail(email)) {
            showAlert("Lỗi", "Email phải có đuôi @gmail.com.");
            return;
        }

        String query = "INSERT INTO customers (name, address, phone_number, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.executeUpdate();
            customerList.add(new Customer(0, name, address, phone, email)); // Thêm khách hàng mới vào danh sách
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra email có đuôi @gmail.com
    private boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com");
    }

    // Hiển thị thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

