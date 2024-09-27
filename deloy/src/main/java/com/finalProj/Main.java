package com.finalProj;

import java.util.Scanner;

public class Main {

    // Phương thức kiểm tra đăng nhập
    private static boolean login(Scanner sc) {
        String username = "admin"; // Tên người dùng
        String password = "123456a@"; // Mật khẩu

        System.out.print("Nhập tên người dùng: ");
        String inputUsername = sc.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String inputPassword = sc.nextLine();

        return username.equals(inputUsername) && password.equals(inputPassword);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Đăng nhập
        boolean isAuthenticated = false;
        while (!isAuthenticated) {
            isAuthenticated = login(sc);
            if (!isAuthenticated) {
                System.out.println("Tên người dùng hoặc mật khẩu không đúng.");
                System.out.println("Vui lòng thử lại.");
            } else {
                System.out.println("Đăng nhập thành công!");
                System.out.println("Chào mừng bạn đến với trang chủ quản lý cửa hàng điện thoại di động!");
            }
        }

        // Thêm các chức năng khác sau khi đăng nhập thành công tại đây
        sc.close();
    }
}
