package com.finalproj.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private void handleManageCustomer(ActionEvent event) {
        switchScene(event, "/com/CustomerView.fxml");
    }

    // Xử lý sự kiện khi nhấn vào nút "Quản lý sản phẩm"
    @FXML
    private void handleManageProduct(ActionEvent event) {
        switchScene(event, "/com/ProductView.fxml");
    }

    // Xử lý sự kiện khi nhấn vào nút "Quản lý hóa đơn"
    @FXML
    private void handleManageInvoice(ActionEvent event) {
        switchScene(event, "/com/InvoiceView.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        switchScene(event, "/com/Login.fxml");
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
}
