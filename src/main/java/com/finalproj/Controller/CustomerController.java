package com.finalproj.Controller;

import com.finalproj.Database.Database;
import com.finalproj.Model.Customer;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerController {

    @FXML
    private TextField nameField, addressField, phoneNumberField, emailField, searchField;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> colCustomerId;

    @FXML
    private TableColumn<Customer, String> colCustomerName, colCustomerAddress, colCustomerPhone, colCustomerEmail;

    @FXML
    private Button addCustomerBtn, editCustomerBtn, deleteCustomerBtn, backButton;

    private ObservableList<Customer> customerList;

    public void initialize() {
        customerList = FXCollections.observableArrayList();
        colCustomerId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colCustomerName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colCustomerAddress.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        colCustomerPhone.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        colCustomerEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        loadCustomers();
    }

    // Tải danh sách khách hàng từ cơ sở dữ liệu
    private void loadCustomers() {
        customerList.clear(); // Xóa danh sách cũ trước khi tải mới
        String query = "SELECT * FROM customers";
        try (Connection conn = Database.getConnection();
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

    // Hiển thị thông tin khách hàng đã chọn lên các trường nhập liệu
    @FXML
    private void showCustomerDetails() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            nameField.setText(selectedCustomer.getName());
            addressField.setText(selectedCustomer.getAddress());
            phoneNumberField.setText(selectedCustomer.getPhoneNumber());
            emailField.setText(selectedCustomer.getEmail());
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

        // Kiểm tra số điện thoại đủ 10 số và bắt đầu bằng số 0
        if (!isValidPhoneNumber(phone)) {
            showAlert("Lỗi", "Số điện thoại gồm 10 số và bắt đầu bằng số 0.");
            return;
        }

        // Kiểm tra trùng số điện thoại
        if (isPhoneNumberDuplicate(phone)) {
            showAlert("Lỗi", "Số điện thoại đã tồn tại trong hệ thống, vui lòng kiểm tra lại!");
            return;
        }

        String query = "INSERT INTO customers (name, address, phone_number, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.executeUpdate();
            showAlert("Thành công", "Thêm khách hàng thành công!");
            clearFields(); // Xóa dữ liệu trong các trường nhập liệu
            loadCustomers(); // Tải lại danh sách khách hàng
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xử lý lưu thông tin khách hàng sau khi chỉnh sửa
    @FXML
    private void handleEditCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneNumberField.getText();
            String email = emailField.getText();

            // Kiểm tra email có hợp lệ hay không
            if (!isValidEmail(email)) {
                showAlert("Lỗi", "Email phải có đuôi @gmail.com.");
                return;
            }

            // Kiểm tra số điện thoại đủ 10 số và bắt đầu bằng số 0
            if (!isValidPhoneNumber(phone)) {
                showAlert("Lỗi", "Số điện thoại phải đủ 10 số và bắt đầu bằng số 0.");
                return;
            }

            // Nếu số điện thoại đã thay đổi, kiểm tra xem có bị trùng hay không
            if (!phone.equals(selectedCustomer.getPhoneNumber()) && isPhoneNumberDuplicate(phone)) {
                showAlert("Lỗi", "Số điện thoại đã tồn tại trong hệ thống.");
                return;
            }

            String query = "UPDATE customers SET name = ?, address = ?, phone_number = ?, email = ? WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setString(3, phone);
                stmt.setString(4, email);
                stmt.setInt(5, selectedCustomer.getId());
                stmt.executeUpdate();

                showAlert("Thành công", "Cập nhật thông tin khách hàng thành công!");
                clearFields(); // Xóa dữ liệu sau khi cập nhật thành công
                loadCustomers(); // Tải lại danh sách khách hàng
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một khách hàng để sửa.");
        }
    }

    // Xử lý xóa khách hàng
    @FXML
    private void handleDeleteCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            String query = "DELETE FROM customers WHERE id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, selectedCustomer.getId());
                stmt.executeUpdate();

                showAlert("Thành công", "Xóa khách hàng thành công!");
                loadCustomers(); // Tải lại danh sách khách hàng
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Lỗi", "Vui lòng chọn một khách hàng để xóa.");
        }
    }

    // Tìm kiếm khách hàng theo số điện thoại
    @FXML
    private void handleSearchCustomer() {
        String searchTerm = searchField.getText();

        // Tìm kiếm theo số điện thoại
        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
        for (Customer customer : customerList) {
            if (customer.getPhoneNumber().contains(searchTerm)) {
                filteredCustomers.add(customer);
            }
        }

        // Cập nhật TableView với danh sách tìm kiếm
        customerTable.setItems(filteredCustomers);

        if (filteredCustomers.isEmpty()) {
            showAlert("Thông báo", "Không tìm thấy khách hàng với số điện thoại này.");
        }
    }

    // Xử lý quay lại màn hình chính
    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        switchScene(event, "/com/Dashboard.fxml"); // Đường dẫn đến tệp FXML của Dashboard
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

    // Kiểm tra email có đuôi @gmail.com
    private boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com");
    }

    // Kiểm tra số điện thoại đủ 10 số và bắt đầu bằng số 0
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("0\\d{9}"); // Bắt đầu bằng 0 và có tổng cộng 10 chữ số
    }

    // Kiểm tra số điện thoại đã tồn tại trong cơ sở dữ liệu
    private boolean isPhoneNumberDuplicate(String phoneNumber) {
        String query = "SELECT COUNT(*) FROM customers WHERE phone_number = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Nếu kết quả trả về lớn hơn 0, nghĩa là đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể kiểm tra trùng lặp số điện thoại.");
        }
        return false;
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
        nameField.clear();
        addressField.clear();
        phoneNumberField.clear();
        emailField.clear();
    }
}
