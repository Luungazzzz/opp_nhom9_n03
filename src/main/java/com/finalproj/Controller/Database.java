package com.finalproj.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/store_management"; // Thay URL phù hợp
    private static final String USER = "root"; // Thay username phù hợp
    private static final String PASSWORD = "291220"; // Thay password phù hợp

    // Phương thức kết nối với cơ sở dữ liệu
    public static Connection connect() {
        Connection connection = null;
        try {
            // Kết nối tới cơ sở dữ liệu
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối tới Database thành công.");
        } catch (SQLException e) {
            System.err.println("Kết nối tới Database thất bại: " + e.getMessage());
        }
        return connection;
    }
}
