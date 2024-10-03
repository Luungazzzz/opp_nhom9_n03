package com.finalproj.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginController {

    @FXML
    private TextField account;

    @FXML
    private PasswordField password;

    private static final String FILE_PATH = "/Files/login.txt";

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = account.getText();
        String pwd = password.getText();

        // Kiểm tra thông tin đăng nhập từ database hoặc thông tin mặc định
        if (username.equals("admin") && pwd.equals("123456") || isValidUser(username, pwd)) {
            saveLoginToFile(username, pwd); // Lưu thông tin đăng nhập vào file
            switchScene(event, "/com/Dashboard.fxml");
        } else {
            showAlert("Đăng nhập thất bại", "Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }

    // Lưu thông tin đăng nhập vào tệp tin
    private void saveLoginToFile(String username, String password) {
        try (FileWriter writer = new FileWriter("src/main/resources" + FILE_PATH, true)) {
            writer.write(username + " " + password + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc thông tin đăng nhập từ tệp tin
    private void loadCredentials() {
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(FILE_PATH))) {
            if (scanner.hasNextLine()) {
                String[] credentials = scanner.nextLine().split(" ");
                if (credentials.length == 2) {
                    String username = credentials[0];
                    String password = credentials[1];
                    account.setText(username);
                    this.password.setText(password);
                }
            }
        } catch (Exception e) {
            showAlert("Lỗi", "Không thể đọc tệp tin login.txt.");
            e.printStackTrace();
        }
    }

    // Chuyển cảnh sang file FXML khác
    private void switchScene(ActionEvent event, String fxmlFilePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFilePath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hiển thị thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Kiểm tra thông tin người dùng trong database
    private boolean isValidUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tồn tại người dùng
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
