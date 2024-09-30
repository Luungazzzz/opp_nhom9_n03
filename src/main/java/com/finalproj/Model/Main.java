package com.finalproj.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Tải file FXML của Login
            Parent root = FXMLLoader.load(getClass().getResource("/com/Login.fxml"));
            primaryStage.setTitle("Quản lý cửa hàng");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để kiểm tra
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
